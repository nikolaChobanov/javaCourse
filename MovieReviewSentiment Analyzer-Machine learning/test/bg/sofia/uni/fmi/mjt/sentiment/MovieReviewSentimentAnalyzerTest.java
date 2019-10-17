package bg.sofia.uni.fmi.mjt.sentiment;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class MovieReviewSentimentAnalyzerTest {


    private static MovieReviewSentimentAnalyzer DB;

    @BeforeClass
    public static void setUp() throws IOException {
        InputStream stopWordsInput = new FileInputStream("src/resources/stopwords.txt");
        InputStream reviewsInput = new FileInputStream("src/resources/reviews.txt");
        OutputStream reviewsOutput = new FileOutputStream("src/resources/reviews.txt",true);

        DB = new MovieReviewSentimentAnalyzer(stopWordsInput, reviewsInput, reviewsOutput);


        String review = "uga buga duga";
        String word = "uga";
        Double reviewScore = 1.0;
        DB.appendReview(review, reviewScore);
    }

    @Test
    public void testCorrectResultWhenGetWordSentiment() {
        String message = "Incorrect  sentiment score returned";
        String word = "uga";

        assertEquals(message, DB.getWordSentiment(word), 1.0, 0);
    }

    @Test
    public void testCorrectReviewSentiment() {
        String message = "Incorrect sentiment score returned";
        String review = "uga buga duga";

        assertEquals(message, DB.getReviewSentiment(review), 1.0, 0);
    }

    @Test
    public void testCorrectReviewSentimentAsName() {
        String message = "Incorrect sentiment score returned as name";
        String review = "uga buga duga";

        assertEquals(message, DB.getReviewSentimentAsName(review), "Negative");
    }

    @Test
    public void testCorrectGetReviewOutput() {
        String message = "Review did not come about";
        Double score = 2.0;

        assertNotNull(message, DB.getReview(score));
    }

    @Test
    public void testGetSentimentDictionarySize() {
        String message = "Incorrect size returned";
        int size = DB.words.size();
        assertEquals(message, DB.getSentimentDictionarySize(), size);
    }

    @Test
    public void testIsStopWordTrue() {
        String message = "Output showed it is not a stop word when it is";
        String stopWord = "because";
        assertTrue(message, DB.isStopWord(stopWord));
    }

    @Test
    public void testGetMostPositiveWords() {
        String message = "Returned Null";
        int numbWords = 1;
        assertNotNull(message, DB.getMostPositiveWords(numbWords));
    }

    @Test
    public void testGetMostFrequentWords() {
        String message = "Returned Null";
        int numbWords = 1;
        assertNotNull(message, DB.getMostFrequentWords(numbWords));
    }

    @Test
    public void testGetMostNegativeWords() {
        String message = "Returned Null";
        int numbWords = 1;
        assertNotNull(message, DB.getMostNegativeWords(numbWords));
    }

}
