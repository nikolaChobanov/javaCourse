package fmi.uni.iostream.checkstyle;

import java.io.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class StyleChecker {


    private static final String WILDCARD_IMPORT_CHECK = "wildcard.import.check.active";
    private static final String STATEMENTS_PER_LINE_CHECK = "statements.per.line.check.active";
    private static final String LENGTH_OF_LINE_CHECK = "length.of.line.check.active";
    private static final String OPENING_BRACKET_CHECK = "opening.bracket.check.active";
    private static final String LINE_LENGTH_LIMIT = "line.length.limit";

    private static final String BRACKET_ERROR = "// FIXME Opening brackets should be placed on the same line as the declaration";
    private static final String WILDCARD_IMPORT_ERROR = "// FIXME Wildcards are not allowed in import statements";
    private static final String LINE_LENGTH_ERROR = "// FIXME Length of line should not exceed %s characters";
    private static final String STATEMENTS_ERROR = "// FIXME Only one statement per line is allowed";

    private static final String DEFAULT_LINE_LENGTH_LIMIT = "100";
    private static final String DEFAULT_CHECK_MODE = "true";

    private Properties properties;
    private Set<CodeCheck> checks;


    public StyleChecker() {

       this(null);
    }


    public StyleChecker(InputStream inputStream) {
        initDefaultProperties();

       if(inputStream!=null){

           try {
              properties.load(inputStream);
           }catch(IOException e){
               properties=new Properties();
               initDefaultProperties();
           }
       }
       initCheckers();
    }


    public void checkStyle(InputStream source, OutputStream output) {

       try (BufferedReader reader=new BufferedReader(new InputStreamReader(source));
           PrintWriter writer =new PrintWriter(output)){

           String line;
           Set<String> lineErrors;

           while((line=reader.readLine()) != null){
               if(line.trim().equals("")){
                   writer.println(line);
                   continue;
               }

               lineErrors=getErrors(line);

               if(!lineErrors.isEmpty()){
                   for(String error : lineErrors){

                       String whitespaces = line.substring(0,line.indexOf(line.trim()));
                       writer.println(whitespaces + error);
                   }
               }
               writer.println(line);
           }

       }catch (IOException ioExc){
           throw new RuntimeException("Error occurred while working",ioExc);
       }

    }




    public void initDefaultProperties(){

        properties=new Properties();

        String[] booleanProps = { WILDCARD_IMPORT_CHECK, STATEMENTS_PER_LINE_CHECK,
                LENGTH_OF_LINE_CHECK, OPENING_BRACKET_CHECK };

        for (String property : booleanProps) {
            properties.setProperty(property, DEFAULT_CHECK_MODE);
        }

        properties.setProperty(LINE_LENGTH_LIMIT, DEFAULT_LINE_LENGTH_LIMIT);

    }


    public void initCheckers() {

        checks = new HashSet<>();

        if (isPropertySet(OPENING_BRACKET_CHECK)) {
            checks.add(new BracketsCheck(BRACKET_ERROR));
        }

        if (isPropertySet((STATEMENTS_PER_LINE_CHECK))) {
            checks.add(new SingleStatementCheck(STATEMENTS_ERROR));
        }
        if (isPropertySet(LENGTH_OF_LINE_CHECK)) {
            int lineLimit = Integer.parseInt(properties.getProperty(LINE_LENGTH_LIMIT));
            checks.add(new LineLengthCheck(LINE_LENGTH_ERROR, lineLimit));
        }

        if (isPropertySet((WILDCARD_IMPORT_CHECK))) {
            checks.add(new WildcardImportCheck(WILDCARD_IMPORT_ERROR));
        }

    }



    public boolean isPropertySet(String propertyName){
        String property=properties.getProperty(propertyName);
        return Boolean.parseBoolean(property);
    }

    private Set<String> getErrors(String line) {
        Set<String> errors = new HashSet<>();

        for (CodeCheck codeCheck : checks) {
            if (codeCheck.checkForError(line)) {
                errors.add(codeCheck.getErrorMessage());
            }
        }

        return errors;
    }
}