package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class RegularOffer implements Offer {


    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;
    private Double totalPrice;

    public RegularOffer(String productName, LocalDate date, String description, double price, double shippingPrice) {
        this.productName = productName.toLowerCase();
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
        this.totalPrice = price + shippingPrice;
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