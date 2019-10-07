package fmi.uni.santaworkshop;

public class Christmas {

    private Workshop workshop;


    private int numberOfKids;

    private int christmasTime;

    public Christmas(Workshop workshop, int numberOfKids, int christmasTime) {
        this.workshop = workshop;
        this.numberOfKids = numberOfKids;
        this.christmasTime = christmasTime;
    }

    public static void main(String[] args) {

        int numberOfKids = 1000;
        int christmasTime = 2000;

        Christmas christmas = new Christmas(new Workshop(), 100, 2000);
        christmas.celebrate();
    }

    public void celebrate() {

        Thread[] kids = new Thread[numberOfKids];
        for (int i = 0; i < kids.length; i++) {
            kids[i] = new Thread(new Kid(workshop));
            kids[i].start();
        }

        try {
            Thread.sleep(christmasTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (workshop) {
            workshop.setChristmasTime();
        }

        for (int i = 0; i < kids.length; i++) {
            try {
                kids[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Workshop getWorkshop() {
        return workshop;
    }
}