package fmi.car.store;

import fmi.car.store.car.Car;
import fmi.car.store.enums.Model;
import fmi.car.store.exception.CarNotFoundException;

import java.util.*;

class CarStore {

    private Set<Car> cars = new TreeSet<>(new DefaultComparator());
    private int totalPrice = 0;

    boolean add(Car car) {

        if (cars.contains(car)) {
            return false;
        } else {

            totalPrice += car.getPrice();
            return cars.add(car);
        }

    }

    boolean addAll(Collection<Car> cars) {
        boolean isAddedAtLeastOne = false;
        for (Car car : cars) {

            if (cars.contains(car)) {

            } else {
                boolean isAdded = add(car);
                if (isAdded) {
                    isAddedAtLeastOne = true;
                    totalPrice += car.getPrice();
                }
            }

        }
        return isAddedAtLeastOne;
    }

     boolean remove(Car car) {
        if (cars.contains(car)) {
            totalPrice -= car.getPrice();
            return cars.remove(car);
        } else {
            return false;
        }
    }

    Collection<Car> getCarsByModel(Model model) {
        TreeSet<Car> col = new TreeSet<>(new DefaultComparator());
        for (Car cur : cars) {

            if (cur.getModel().equals(model)) {
                col.add(cur);
            }
        }

        return col;
    }

    Car getCarByRegistrationNumber(String registrationNumber) {


        for (Car cur : cars) {
            if (cur.getRegistrationNumber().equals(registrationNumber)) {
                return cur;
            }
        }
        String message = String.format("Car.Car with registration number %s not found", registrationNumber);
        throw new CarNotFoundException(message);
    }


     Collection<Car> getCars() {

        return cars;
    }

     Collection<Car> getCars(Comparator<Car> comparator) {
        TreeSet<Car> byComparator = new TreeSet<>(comparator);
        byComparator.addAll(cars);
        return byComparator;
    }


    Collection<Car> getCars(Comparator<Car> comparator, boolean isReversed) {
        if (isReversed) {
            return getCars(Collections.reverseOrder(comparator));
        }
        return getCars(comparator);
    }

    int getNumberOfCars() {

        return cars.size();
    }

     int getTotalPriceForCars() {
        return totalPrice;
    }
}