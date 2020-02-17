package bg.sofia.uni.fmi.mjt.authroship.detection;

import java.io.*;
import java.util.*;

public class AuthorshipDetectorImpl implements AuthorshipDetector {


    private ArrayList<String> signatures;
    private double[] weights;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {

        this.weights = weights;
        this.signatures = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(signaturesDataset))) {

            String line = br.readLine();
            while (line != null) {

                signatures.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {

        isInputStreamValid(mysteryText);

        Map<String, Integer> countByWords = new HashMap<String, Integer>();
        double wordCounter = 0;
        double avgSymbolCount = 0;
        double phraseCounter = 0;
        double sentenceCounter = 0;
        LinguisticSignature signature;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText))) {

            String line = br.readLine();
            while (line != null) {

                String[] split = line.split("\\s+");

                for (String word : split) {

                    //Count sentences without the last one
                    if (word.matches(".*[.!?]{1}.*")) {
                        sentenceCounter++;
                    }
                    //Count phrases
                    if (word.matches(".*[:;,]{1}.*")) {
                        phraseCounter += 2;
                    }
                    if (isAlpha(word)) {
                        wordCounter++;
                        word = cleanUp(word);
                        //Collection for every word and how many times it has occurred in the file
                        Integer count = countByWords.get(word);
                        if (count != null) {
                            countByWords.put(word, count + 1);
                        } else {
                            countByWords.put(word, 1);
                        }
                        // count all the symbols in the file
                        String[] symbols = word.split("(?!^)");
                        avgSymbolCount += symbols.length;
                    }
                }
                line = br.readLine();
            }
            //for EOF sentence
            sentenceCounter++;

            double hapaxRatio = calculateHapaxRatio(countByWords);

            ArrayList<Double> featuresValues = new ArrayList<>();

            featuresValues.add(avgSymbolCount / wordCounter);
            featuresValues.add(countByWords.size() / wordCounter);
            featuresValues.add(hapaxRatio / wordCounter);
            featuresValues.add(wordCounter / sentenceCounter);
            featuresValues.add(phraseCounter / sentenceCounter);

            signature = createSignature(featuresValues);

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return signature;
    }


    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature, LinguisticSignature secondSignature) {

        double sabResult = 0;
        EnumMap<FeatureType, Double> firstFeatures = (EnumMap<FeatureType, Double>) firstSignature.getFeatures();
        EnumMap<FeatureType, Double> secondFeatures = (EnumMap<FeatureType, Double>) secondSignature.getFeatures();

        int featureCounter = 0;
        for (Map.Entry<FeatureType, Double> entry : firstFeatures.entrySet()) {

            Double secondValue = secondFeatures.get(entry.getKey());
            sabResult += (Math.abs(entry.getValue() - secondValue) * weights[featureCounter]);
            featureCounter++;
        }

        return sabResult;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {

        isInputStreamValid(mysteryText);

        LinguisticSignature signatureToMatch = calculateSignature(mysteryText);
        String currentName;
        double currentResult;
        String bestName = null;
        double bestResult = Double.MAX_VALUE;
        ArrayList<Double> featureValues = new ArrayList<>();
        for (String line : signatures) {

            currentName = "";
            line = line.replaceAll(",", "");
            String[] split = line.split("\\s+");

            for (int i = 0; i < split.length; ) {
                if (isAlpha(split[i])) {
                    currentName += split[i] + " ";
                    i++;
                } else {

                    Map<FeatureType, Double> firstFeatures = new EnumMap<FeatureType, Double>(FeatureType.class);

                    for (FeatureType feat : FeatureType.values()) {
                        firstFeatures.put(feat, Double.valueOf(split[i]));
                        i++;
                    }

                    LinguisticSignature compareTo =
                            new LinguisticSignature((EnumMap<FeatureType, Double>) firstFeatures);
                    currentResult = calculateSimilarity(signatureToMatch, compareTo);

                    if (bestResult > currentResult) {
                        bestName = currentName;
                        bestResult = currentResult;
                    }
                }
            }
        }

        bestName = bestName.strip();
        return bestName;
    }


    public LinguisticSignature createSignature(ArrayList<Double> values) {

        Map<FeatureType, Double> features = new EnumMap<FeatureType, Double>(FeatureType.class);

        int i = 0;
        for (FeatureType feat : FeatureType.values()) {
            features.put(feat, Double.valueOf(values.get(i)));
            i++;
        }

        return new LinguisticSignature((EnumMap<FeatureType, Double>) features);
    }

    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll("^[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\" +
                        "]+|[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+$", "");
    }

    public boolean isAlpha(String name) {
        return name.matches(".*[a-zA-Z]+.*");
    }

    public double calculateHapaxRatio(Map<String, Integer> countByWords) {

        double hapaxRatio = 0;

        for (Map.Entry<String, Integer> entry : countByWords.entrySet()) {

            Integer value = entry.getValue();
            if (value == 1) {
                hapaxRatio++;
            }
        }

        return hapaxRatio;
    }

    public void isInputStreamValid(InputStream mysteryText) {

        if (mysteryText == null) {
            throw new IllegalArgumentException();
        }

    }

}
