package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class PriceStatistic {

    private LocalDate date;
    private String productName;
    private TreeSet<Offer> offers;

    public PriceStatistic(LocalDate date, String productName, HashSet<Offer> offers) {
        this.date = date;
        this.productName = productName;
        this.offers = new TreeSet<Offer>(new Comparator<Offer>() {
            @Override
            public int compare(Offer o1, Offer o2) {
                if (o1.getTotalPrice() > o2.getTotalPrice()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });


        Iterator<Offer> iter = offers.iterator();
        Offer currentOffer;
        while (iter.hasNext()) {

            currentOffer = iter.next();

            if (currentOffer.getDate().isEqual(date)) {

                this.offers.add(currentOffer);

            }
        }
    }

    public LocalDate getDate() {
        if (date == null) {
            throw new UnsupportedOperationException();
        }
        return date;
    }

    /**
     * Returns the lowest total price from the offers
     * for this product for the specific date.
     */
    public double getLowestPrice() {

        if (offers.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return offers.first().getTotalPrice();

    }

    /**
     * Return the average total price from the offers
     * for this product for the specific date.
     */
    public double getAveragePrice() {
        if (offers.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        double totalPrice = 0;
        int counter = 0;

        Iterator<Offer> iter = offers.iterator();
        Offer currentOffer;
        while (iter.hasNext()) {

            currentOffer = iter.next();
            totalPrice += currentOffer.getTotalPrice();
            counter++;


        }
        return totalPrice / counter;
    }

}
