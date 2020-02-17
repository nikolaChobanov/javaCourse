package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.*;

public class ShoppingDirectoryImpl implements ShoppingDirectory {

    TreeSet<Offer> offers;      // All offers that are not duplicates sorted

    public ShoppingDirectoryImpl() {

        offers = new TreeSet<>(new Comparator<Offer>() {

            @Override
            public int compare(Offer o1, Offer o2) {


                Double a = o1.getTotalPrice();
                Double b = o2.getTotalPrice();
                if (o1.getDate().isEqual(o2.getDate()) && a.equals(b)
                        && o1.getProductName().equals(o2.getProductName())) {
                    return 0;
                }


                if (o1.getDate().isBefore(o2.getDate())) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }
//Helper function to check if a product with such name exists in offers
    public boolean exists(String productName) {
        Iterator<Offer> iter = offers.iterator();
        Offer currentOffer;

        while (iter.hasNext()) {

            currentOffer = iter.next();

            if (currentOffer.getProductName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {


        if (productName == null) {
            throw new IllegalArgumentException();
        }

        productName = productName.toLowerCase();
        if ((exists(productName) == false)) {
            String message = String.format("The product %s you search for doesn't exist in the database", productName);
            throw new ProductNotFoundException(message);
        }

        TreeSet<Offer> allFoundOffers = new TreeSet<>(new Comparator<Offer>() {
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

            if (currentOffer.getProductName().equals(productName)) {

                LocalDate currentDate = LocalDate.now();
                LocalDate lastMonth = currentDate.minusMonths(1);
                if (currentOffer.getDate().isAfter(lastMonth))
                    allFoundOffers.add(currentOffer);
            }

        }

        return allFoundOffers;
    }

    @Override
    public Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException {

        if (productName == null) {
            String message = String.format("Invalid productName %s", productName);
            throw new IllegalArgumentException(message);

        }
        productName = productName.toLowerCase();
        if (!(exists(productName))) {
            String message = String.format("The product %s you search for doesn't exist in the database", productName);
            throw new ProductNotFoundException(message);
        }
        TreeSet<Offer> allOffers = (TreeSet<Offer>) findAllOffers(productName);
        if (allOffers.isEmpty()) {
            String message = String.format("There are no offers for %s for this month", productName);
            throw new NoOfferFoundException(message);
        }


        return allOffers.first();
    }

    @Override
    public Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException {

        if (productName == null) {
            String message = String.format("Invalid productName %s", productName);
            throw new IllegalArgumentException(message);
        }
        productName = productName.toLowerCase();
        TreeSet<PriceStatistic> allFoundOffers = new TreeSet<>(new Comparator<PriceStatistic>() {
            @Override
            public int compare(PriceStatistic o1, PriceStatistic o2) {
                if (o1.getDate().isBefore(o2.getDate())) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        Iterator<Offer> iter = offers.iterator();
        Offer currentOffer;

        LocalDate currentDate = null;
        String currentName = null;
        HashSet<Offer> offersForDay = new HashSet<>();
        while (iter.hasNext()) {

            currentOffer = iter.next();

            if (currentOffer.getProductName().equals(productName)) {

                if (currentDate == null || currentName == null) {
                    currentDate = currentOffer.getDate();
                    currentName = currentOffer.getProductName();
                }

                if (currentOffer.getDate().isEqual(currentDate)) {

                    offersForDay.add(currentOffer);
                } else {

                    PriceStatistic priceStat = new PriceStatistic(currentDate, currentName, offersForDay);
                    allFoundOffers.add(priceStat);
                    offersForDay.clear();
                    currentDate = currentOffer.getDate();
                    currentName = currentOffer.getProductName();
                    if (currentOffer.getDate().isEqual(currentDate)) {

                        offersForDay.add(currentOffer);
                    }
                }
               /* if (!iter.hasNext()) {

                    PriceStatistic priceStat = new PriceStatistic(currentDate, currentName, offersForDay);
                    allFoundOffers.add(priceStat);
                    offersForDay.clear();

                }
*/
            }
        }

        if (!(offersForDay.isEmpty())) {
            PriceStatistic priceStat = new PriceStatistic(currentDate, currentName, offersForDay);
            allFoundOffers.add(priceStat);
        }

        if (allFoundOffers.isEmpty()) {

            String message = String.format("No such product %s exists in the database", productName);
            throw new ProductNotFoundException(message);
        }

        return allFoundOffers;
    }

    @Override
    public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {


        if (offer == null) {
            String message = String.format("Offer was null ");
            throw new IllegalArgumentException(message);
        }

        String name = offer.getProductName().trim().toLowerCase();
        offer.setProductName(name);

        if ((offers.add(offer)) == false) {
            String message = String.format("This offer %s has already been submitted ", offer.getProductName());
            throw new OfferAlreadySubmittedException(message);
        }
    }
}
