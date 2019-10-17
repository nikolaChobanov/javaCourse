package bg.sofia.uni.fmi.mjt.sentiment;

import bg.sofia.uni.fmi.mjt.sentiment.interfaces.SentimentAnalyzer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {
    // reviews stores the original input from reviews.txt line by line
    HashMap<String, Double> reviews = new HashMap<>();
    //words tha should be discarded
    HashSet<String> stopWords = new HashSet<>();
    //Set containing unique words
    HashSet<Word> words;
    PrintWriter printWriter;

    @Override
    public boolean equals(Object o) {
        return ((MovieReviewSentimentAnalyzer) o).stopWords.equals(this.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public MovieReviewSentimentAnalyzer(InputStream stopwordsInput, InputStream reviewsInput,
                                        OutputStream reviewsOutput) {
        printWriter = new PrintWriter(reviewsOutput);

        words = new HashSet<>();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stopwordsInput, Charset.forName("UTF-8")));) {
            while ((line = br.readLine()) != null) {
                stopWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        line = null;
        try (BufferedReader b = new BufferedReader(new InputStreamReader(reviewsInput, Charset.forName("UTF-8")));) {
            double score;
            while ((line = b.readLine()) != null) {
                score = Double.parseDouble(String.valueOf(line.charAt(0)));
                appendReview(line, score);
                reviews.put(line, score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getReviewSentiment(String review) {
        double score = 0;
        double amount = 0;
        double current = -1;
        review = review.toLowerCase();
        String[] splitWords = review.split("\\s*(,|`|-|'|;|:|\\s)\\s*");

        for (String word : splitWords) {
            current = getWordSentiment(word);
            if (current != -1) {
                score += current;
                amount++;
            }
        }
        return score / amount;
    }

    @Override
    public String getReviewSentimentAsName(String review) {
        String name;
        final double neg = 0.4999;
        final double sNeg = 1.4999;
        final double neu = 2.4999;
        final double sPos = 3.4999;


        double score = getReviewSentiment(review);
        if (score >= 0 && score < neg) {
            name = "Negative";
            return name;
        } else if (score < sNeg) {
            name = "Somewhat negative";
            return name;
        } else if (score < neu) {
            name = "Neutral";
            return name;
        } else if (score < sPos) {
            name = "Somewhat positive";
            return name;
        } else if (score >= sPos) {
            name = "Positive";
            return name;
        }

        return null;
    }

    @Override
    public double getWordSentiment(String word) {
        word = word.toLowerCase();

        double sentScore = -1;
        Iterator iter = words.iterator();
        while (iter.hasNext()) {

            Word w = (Word) iter.next();
            if (w.getWord().equals(word)) {

                sentScore = (w.getScore() / w.getNumOccurence());
                break;
            }
        }
        return sentScore;
    }

    @Override
    public String getReview(double sentimentValue) {
        if (sentimentValue < 0) {
            throw new IllegalArgumentException();
        }


        return reviews.entrySet().stream()
                .filter(e -> e.getValue().equals(sentimentValue))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void appendReview(String review, double sentimentValue) {

        printWriter.println(review);

        String regex = "^[a-zA-Z0-9]{2,}+$";
        Pattern pattern = Pattern.compile(regex);
        review = review.toLowerCase();
        String[] splitWords = review.split("\\s*(,|`|-|'|;|:|\\s)\\s*");
        for (String word : splitWords) {
            if (splitWords[0] == word) {
                continue;
            }
            if (!(isStopWord(word))) {
                Matcher matcher = pattern.matcher(word);

                if (matcher.matches() == true) {

                    if ((words.stream().filter(x -> x.getWord().equals(word)).findAny().orElse(null)) != null) {

                        Iterator iter = words.iterator();
                        while (iter.hasNext()) {

                            Word w = (Word) iter.next();
                            if (w.getWord().equals(word)) {

                                w.setScore(w.getScore() + sentimentValue);
                                w.incrOccurence();
                            }
                        }
                    } else {

                        words.add(new Word(sentimentValue, 1, word));
                    }

                }
            }
        }
    }

    @Override
    public int getSentimentDictionarySize() {
        return words.size();
    }

    @Override
    public boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    @Override
    public Collection<String> getMostFrequentWords(int n) {
        return words.stream().sorted(Comparator.comparing(Word::getNumOccurence).reversed()).map(Word::getWord).limit(n).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getMostPositiveWords(int n) {
        return words.stream().sorted(Comparator.comparing(Word::getScore).reversed()).map(Word::getWord).limit(n).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getMostNegativeWords(int n) {
        return words.stream().sorted(Comparator.comparing(Word::getScore)).map(Word::getWord).limit(n).collect(Collectors.toList());
    }

}