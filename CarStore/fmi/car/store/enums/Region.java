package fmi.car.store.enums;

import java.util.Random;

public enum Region {
    SOFIA("СВ"), BURGAS("A"), VARNA("B"), PLOVDIV("PB"), RUSE("P"), GABROVO("EB"), VIDIN("BH"), VRATSA("BP");

    private final String prefix;
    private int counter = 100;

    private Region(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRegistrationNumber() {
        int value = counter;
        counter++;
        return String.format("%s%d%c%c", prefix, value, getRandomChar(), getRandomChar());
    }

    private char getRandomChar() {
        Random random = new Random();
        return (char) (random.nextInt(26) + 'A');
    }
}
