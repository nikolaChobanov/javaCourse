package fmi.car.store.car;

import fmi.car.store.enums.EngineType;
import fmi.car.store.enums.Model;

public interface Car {


    /**
     * Returns the model of the fmi.car.store.car.
     */
    Model getModel();

    /**
     * Returns the year of manufacture of the fmi.car.store.car.
     */
    int getYear();

    /**
     * Returns the price of the fmi.car.store.car.
     */
    int getPrice();

    /**
     * Returns the engine type of the fmi.car.store.car.
     */
    EngineType getEngineType();

    /**
     * Returns the unique registration number of the fmi.car.store.car.
     */
    String getRegistrationNumber();


}
