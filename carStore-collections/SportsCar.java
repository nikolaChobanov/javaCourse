public class SportsCar implements Car {


    private Model model;
    private int year;
    private int price;
    private EngineType engineType;
    private Region region;
    private String registrationNumber;

    public SportsCar(Model model, int year, int price, EngineType engineType, Region region) {

        this.model = model;
        this.year = year;
        this.price = price;
        this.engineType = engineType;
        this.region = region;
        this.registrationNumber = region.getRegistrationNumber();

    }

    public Model getModel() {
        return this.model;
    }


    public int getYear() {
        return this.year;
    }

    public int getPrice() {
        return this.price;
    }

    public EngineType getEngineType() {
        return this.engineType;
    }

    public String getRegistrationNumber() {

        return registrationNumber;
    }


}


