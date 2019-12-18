public class ResponseOverflowException extends Exception {
    public ResponseOverflowException(String errorMessage) {
        super(errorMessage + " was too large!");
    }
}
