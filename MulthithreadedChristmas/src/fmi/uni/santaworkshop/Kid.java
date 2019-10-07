package fmi.uni.santaworkshop;

import java.util.Random;

public class Kid implements Runnable {

    private static final int MAX_TIME = 3000;
    private static Random random = new Random();

    private Workshop workshop;

    Gift gift;

    public Kid(Workshop workshop) {
        this.workshop = workshop;
    }

    private void makeWish() {
        gift = Gift.getGift();
        workshop.postWish(gift);
    }

    @Override
    public void run() {

        try {
            Thread.sleep(random.nextInt(MAX_TIME));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        makeWish();
    }
}
