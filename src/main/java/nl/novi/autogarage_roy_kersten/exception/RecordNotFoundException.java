package nl.novi.autogarage_roy_kersten.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        super();
    }
    public RecordNotFoundException(String message) {
        super(message);
    }
}
