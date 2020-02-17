package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class OfferAlreadySubmittedException extends RuntimeException {

    private static final long serialVersionUID = 1460125818886683558L;

    public OfferAlreadySubmittedException() {
    }

    public OfferAlreadySubmittedException(String message) {
        super(message);
    }
}
