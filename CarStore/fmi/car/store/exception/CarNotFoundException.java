package fmi.car.store.exception;

public class CarNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1460125818886683558L;

    public CarNotFoundException(String message) {
        super(message);
    }
}
