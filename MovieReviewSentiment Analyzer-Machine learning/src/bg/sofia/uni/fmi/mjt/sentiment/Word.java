package bg.sofia.uni.fmi.mjt.sentiment;

public class Word {

    private double score;
    private double numOccurence;
    private String word;

    public Word(double score, double numOccurence, String word) {
        this.score = score;
        this.numOccurence = numOccurence;
        this.word = word;
    }


    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getNumOccurence() {
        return numOccurence;
    }

    public void incrOccurence() {
        numOccurence = numOccurence + 1;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
