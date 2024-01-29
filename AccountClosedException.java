
public class AccountClosedException extends Exception {

    public AccountClosedException() {
        super();
    }
    
    public AccountClosedException(int acctNum) {
        super("Error: Account " +acctNum +" is closed.");
    }
    
    public AccountClosedException(String str) {
        super(str);
    }
    
    public String getErrorMessage() {
        return super.getMessage();
    }
}
