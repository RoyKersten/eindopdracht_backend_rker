package nl.novi.autogarage_roy_kersten.exception;

public class BadRequestException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super();
    }
    public BadRequestException(String message) {
        super(message);
    }

}
