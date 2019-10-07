package fmi.uni.santaworkshop;

public class Santa extends Thread {

    private Workshop workshop;

    public Santa(Workshop workshop) {
        this.workshop = workshop;
    }

    @Override
    public void run() {

    }
}
