import java.util.*;

public class CarStore {

    private Set<Car> cars = new TreeSet<>(new DefaultComparator());
    private int totalPrice = 0;

    public boolean add(Car car) {

        if (cars.contains(car)) {
            return false;
        } else {

            totalPrice += car.getPrice();
            return cars.add(car);
        }

    }

    public boolean addAll(Collection<Car> cars) {
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

    public boolean remove(Car car) {
        if (cars.contains(car)) {
            totalPrice -= car.getPrice();
            return cars.remove(car);
        } else {
            return false;
        }
    }

    public Collection<Car> getCarsByModel(Model model) {
        TreeSet<Car> col = new TreeSet<>(new DefaultComparator());
        for (Car cur : cars) {

            if (cur.getModel().equals(model)) {
                col.add(cur);
            }
        }

        return col;
    }

    public Car getCarByRegistrationNumber(String registrationNumber) {


        for (Car cur : cars) {
            if (cur.getRegistrationNumber().equals(registrationNumber)) {
                return cur;
            }
        }
        String message = String.format("Car with registration number %s not found", registrationNumber);
        throw new CarNotFoundException(message);
    }


    public Collection<Car> getCars() {

        return cars;
    }

    public Collection<Car> getCars(Comparator<Car> comparator) {
        TreeSet<Car> byComparator = new TreeSet<>(comparator);
        byComparator.addAll(cars);
        return byComparator;
    }


    public Collection<Car> getCars(Comparator<Car> comparator, boolean isReversed) {
        if (isReversed) {
            return getCars(Collections.reverseOrder(comparator));
        }
        return getCars(comparator);
    }

    public int getNumberOfCars() {

        return cars.size();
    }

    public int getTotalPriceForCars() {
        return totalPrice;
    }
}