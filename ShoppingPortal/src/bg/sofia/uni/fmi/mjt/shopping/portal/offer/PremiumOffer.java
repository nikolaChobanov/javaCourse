package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class PremiumOffer implements Offer {

    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;
    private double discount;
    private Double totalPrice;
    private static final double LOWER_BOUND = 0.00;
    private static final double UPPER_BOUND = 100.00;

    public PremiumOffer(String productName, LocalDate date, String description, double price,
                        double shippingPrice, double discount) {
        this.productName = productName.toLowerCase();
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
        discount = round(discount);
        if (discount > LOWER_BOUND || discount < UPPER_BOUND) {
            this.discount = ((price + shippingPrice) * discount) / UPPER_BOUND;

            this.totalPrice = (price + shippingPrice) - this.discount;
        } else {
            this.totalPrice = (price + shippingPrice);
        }

    }
//Helper fucntion to round double to 2 points after decimal
    public static double round(double value) {


        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }

}
