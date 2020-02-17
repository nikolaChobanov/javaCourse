package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class NoOfferFoundException extends RuntimeException {

    private static final long serialVersionUID = 1460125818886683558L;

    public NoOfferFoundException() {
    }

    public NoOfferFoundException(String message) {
        super(message);
    }
}
