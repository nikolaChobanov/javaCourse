package fmi.uni.iostream.checkstyle;

public class WildcardImportCheck extends CodeCheck{

    public WildcardImportCheck(String errorMessage){
        super(errorMessage);
    }

    @Override
    public boolean checkForError(String line){
        /*if(!(line.trim().startsWith("Import"))){
            return line.contains(".*");
        }
        return false;
*/
        return line.trim().split(";")[0].endsWith(".*");
    }
}
