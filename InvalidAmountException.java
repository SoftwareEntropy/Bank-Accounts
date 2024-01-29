
public class InvalidAmountException extends Exception {

    public InvalidAmountException() {
        super();
    }
    
    public InvalidAmountException(double req) {
        super("Error: $" +req +" is an invalid amount.");
    }
    
    public String getErrorMessage() {
        return super.getMessage();
    }
}
