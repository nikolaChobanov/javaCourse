import bg.sofia.uni.fmi.mjt.shopping.portal.PriceStatistic;
import bg.sofia.uni.fmi.mjt.shopping.portal.ShoppingDirectory;
import bg.sofia.uni.fmi.mjt.shopping.portal.ShoppingDirectoryImpl;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.PremiumOffer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.RegularOffer;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.TreeSet;

public class Main {


    public static void main(String[] args) {

        ShoppingDirectory newDir = new ShoppingDirectoryImpl();
        Offer offer1 = new RegularOffer("MILK", LocalDate.now(), "dairy Product", 2, 0.5);
        Offer offer2 = new RegularOffer("cheese", LocalDate.now(), "dairy Product fermented", 5, 1.5);
        Offer offer3 = new RegularOffer("MILK", LocalDate.now(), "chicken eggs Product", 10, 0.7);
        Offer offer4 = new RegularOffer("milk", LocalDate.now(), "chicken eggs Product", 6, 0.3);
        Offer offer5 = new RegularOffer("milk", LocalDate.now(), "chicken eggs Product", 3, 0.5);
       Offer offer15 = new RegularOffer("milk", LocalDate.now(), "chicken eggs Product", 3, 0.5);
        Offer offer6 = new RegularOffer("milk", LocalDate.now(), "chicken eggs Product", 8, 0.1);
        Offer offer7= new RegularOffer("milk", LocalDate.now().minusMonths(2), "chicken eggs Product", 1000, 0.7);
        Offer offer8 = new RegularOffer("milk", LocalDate.now().minusMonths(1), "chicken eggs Product", 600, 0.3);
        Offer offer78= new RegularOffer("milk", LocalDate.now().minusMonths(2), "chicken eggs Product", 10, 0.7);
        Offer offer87 = new RegularOffer("milk", LocalDate.now().minusMonths(1), "chicken eggs Product", 6, 0.3);
        Offer soffer1 = new PremiumOffer("milk", LocalDate.now(), "chicken eggs Product", 2, 0.7, 20.00);
        Offer soffer2 = new PremiumOffer("milk", LocalDate.now(), "chicken eggs Product", 50, 0.7, 50.00);
        Offer soffer3 = new PremiumOffer("cheese", LocalDate.now(), "chicken eggs Product", 500, 0.7, 30.00);
        Offer soffer4 = new PremiumOffer("cheese", LocalDate.now(), "chicken eggs Product", 100, 0.7, 50.00);
        Offer soffer5 = new PremiumOffer("chEeSe", LocalDate.now(), "chicken eggs Product", 5000, 0.7, 40.00);
        Offer soffer6 = new PremiumOffer("cheese", LocalDate.now(), "chicken eggs Product", 2500, 0.7, 60.00);
        Offer soffer7 = new PremiumOffer("chEeSe", LocalDate.now(), "chicken eggs Product", 1500, 0.7, 70.00);
        Offer offer9 = new RegularOffer("Eggs", LocalDate.now(), "chicken eggs Product", 61, 0.3);
        Offer offer10 = new RegularOffer("egGs", LocalDate.now(), "chicken eggs Product", 16, 0.3);
        Offer offer11 = new RegularOffer("eggS", LocalDate.now(), "chicken eggs Product", 46, 0.3);


        newDir.submitOffer(offer1);
        newDir.submitOffer(offer2);
        newDir.submitOffer(offer3);
        newDir.submitOffer(offer4);
        newDir.submitOffer(offer5);
  //   newDir.submitOffer(offer15);
        newDir.submitOffer(offer6);
        newDir.submitOffer(offer7);
        newDir.submitOffer(offer8);
        newDir.submitOffer(soffer1);
        newDir.submitOffer(soffer2);
        newDir.submitOffer(soffer3);
        newDir.submitOffer(soffer4);
        newDir.submitOffer(soffer5);
        newDir.submitOffer(soffer6);
        newDir.submitOffer(soffer7);
        newDir.submitOffer(offer9);
   //     newDir.submitOffer(offer10);
    //    newDir.submitOffer(offer11);
        newDir.submitOffer(offer87);
        newDir.submitOffer(offer78);




        TreeSet<Offer> a = (TreeSet<Offer>) newDir.findAllOffers("milk");
        Offer af = newDir.findBestOffer("milk");

        Iterator<Offer> iterator = a.iterator();

        System.out.println(af.getProductName() + " " + af.getPrice() + " " + af.getDate() + " " + af.getDescription() + " " + af.getTotalPrice() + " " + af.getShippingPrice());
        while (iterator.hasNext()) {
            Offer b = iterator.next();

            System.out.println(b.getProductName() + " " + b.getPrice() + " " + b.getDate() + " " + b.getDescription() + " " + b.getTotalPrice() + " " + b.getShippingPrice());
        }

        System.out.println("sikidjem ");


        TreeSet<Offer> omg = (TreeSet<Offer>) newDir.findAllOffers("cheese");
        Offer wat = newDir.findBestOffer("cheese");

        Iterator<Offer> iteratorb = omg.iterator();

        System.out.println(wat.getProductName() + " " + wat.getPrice() + " " + wat.getDate() + " " + wat.getDescription() + " " + wat.getTotalPrice() + " " + wat.getShippingPrice());
        while (iteratorb.hasNext()) {
            Offer b = iteratorb.next();

            System.out.println(b.getProductName() + " " + b.getPrice() + " " + b.getDate() + " " + b.getDescription() + " " + b.getTotalPrice() + " " + b.getShippingPrice());
        }

        System.out.println("sikidjem ");


        TreeSet<PriceStatistic> abckk = (TreeSet<PriceStatistic>) newDir.collectProductStatistics("milk");
        Iterator<PriceStatistic> iteratora = abckk.iterator();


        while (iteratora.hasNext()) {
            PriceStatistic c = iteratora.next();

            System.out.println(c.getDate() + " "+ c.getAveragePrice() + " " + c.getLowestPrice() + " ");
        }




        TreeSet<Offer> omga = (TreeSet<Offer>) newDir.findAllOffers("eggs");
        Offer ddd = newDir.findBestOffer("eggs");

        Iterator<Offer> iteratorc = omga.iterator();

        System.out.println(ddd.getProductName() + " " + ddd.getPrice() + " " + ddd.getDate() + " " + ddd.getDescription() + " " + ddd.getTotalPrice() + " " + ddd.getShippingPrice());
        while (iteratorc.hasNext()) {
            Offer b = iteratorc.next();

            System.out.println(b.getProductName() + " " + b.getPrice() + " " + b.getDate() + " " + b.getDescription() + " " + b.getTotalPrice() + " " + b.getShippingPrice());
        }

        System.out.println("sikidjem ");




        TreeSet<PriceStatistic> abck = (TreeSet<PriceStatistic>) newDir.collectProductStatistics("cheese");
        Iterator<PriceStatistic> iterat = abck.iterator();


        while (iterat.hasNext()) {
            PriceStatistic c = iterat.next();

            System.out.println(c.getDate() + " "+ c.getAveragePrice() + " " + c.getLowestPrice() + " ");
        }


        System.out.println("sikidjem2 ");

        TreeSet<PriceStatistic> bckk = (TreeSet<PriceStatistic>) newDir.collectProductStatistics("eggs");
        Iterator<PriceStatistic> teratora = bckk.iterator();


        while (teratora.hasNext()) {
            PriceStatistic c = teratora.next();

            System.out.println(c.getDate() + " "+ c.getAveragePrice() + " " + c.getLowestPrice() + " ");
        }

      /*  TreeSet<Offer> ab= (TreeSet<Offer>) newDir.findAllOffers("htyh");
        Offer afb=newDir.findBestOffer("migdfgdlk");
*/
      /*  Iterator<Offer> iterator = omg.iterator();

        System.out.println(af.getProductName() + " " + af.getPrice() + " "+ af.getDate() + " "+ af.getDescription() + " "+ af.getTotalPrice() + " "+ af.getShippingPrice());
        while (iterator.hasNext()) {
            Offer b = iterator.next();

            System.out.println(b.getProductName() + " "+ b.getPrice() + " "+ b.getDate() + " "+ b.getDescription() + " "+ b.getTotalPrice() + " "+ b.getShippingPrice());
        }*/
    }
}
