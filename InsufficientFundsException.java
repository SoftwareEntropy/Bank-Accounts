
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
        super();
    }
    
    public InsufficientFundsException(double req, double balance) {
        super("Error: Insufficient funds. Requested transaction amount: $" +req +"   Balance: $" +balance);
    }
    
    public InsufficientFundsException(String str) {
        super(str);
    }
    
    public String getErrorMessage() {
        return super.getMessage();
    }
}
