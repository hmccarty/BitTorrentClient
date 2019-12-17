public class NoAvailablePortException extends Exception {
    public NoAvailablePortException(String errorMessage) {
        super(errorMessage + ", all ports are currently busy!");
    }
}
