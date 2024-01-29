
public class InvalidAccountException extends Exception{
    
    public InvalidAccountException() {
        super("Error: Account not found");
    }
    
    public InvalidAccountException(int accountNum) {
        super("Error: Account " +accountNum + " not found.");
    }
    
    public InvalidAccountException(String str) {
        super(str);
    }
    
    public String getErrorMessage() {
        return super.getMessage();
    }
}
