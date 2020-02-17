package fmi.car.store.car;

import fmi.car.store.enums.EngineType;
import fmi.car.store.enums.Model;
import fmi.car.store.enums.Region;

public class OrdinaryCar implements Car {

    private Model model;
    private int year;
    private int price;
    private EngineType engineType;
    private Region region;
    private String registrationNumber;

    public OrdinaryCar(Model model, int year, int price, EngineType engineType, Region region) {

        this.model = model;
        this.year = year;
        this.price = price;
        this.engineType = engineType;
        this.region = region;
        this.registrationNumber = region.getRegistrationNumber();

    }

    public Model getModel() {
        return model;
    }


    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public String getRegistrationNumber() {

        return registrationNumber;
    }
   /* @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Car.Car other = (Car.Car) obj;
        return registrationNumber.equals(other.registrationNumber);
    }
*/
}


