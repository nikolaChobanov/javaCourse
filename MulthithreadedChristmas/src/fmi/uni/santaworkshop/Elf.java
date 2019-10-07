package fmi.uni.santaworkshop;

public class Elf extends Thread {

    private int id;
    private Workshop workshop;

    private int totalGiftsCrafted;

    public Elf(int id, Workshop workshop) {
        this.id = id;
        this.workshop = workshop;
    }

    @Override
    public void run() {
        craftGift();
    }

    public void craftGift() {
        Gift gift = null;

        while ((gift = workshop.nextGift()) != null) {
            try {
                Thread.sleep(gift.getCraftTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalGiftsCrafted++;
        }

        System.out.println("Elf #" + id + " created " + totalGiftsCrafted + " gifts");
    }

    public int getTotalGiftsCrafted() {
        return totalGiftsCrafted;
    }
}
