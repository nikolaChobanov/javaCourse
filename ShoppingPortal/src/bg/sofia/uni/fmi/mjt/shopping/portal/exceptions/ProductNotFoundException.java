package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class ProductNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1460125818886683558L;

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
