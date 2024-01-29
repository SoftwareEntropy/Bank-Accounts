
public class InvalidMenuSelectionException extends Exception {

    public InvalidMenuSelectionException() {
        super();
    }
    
    public InvalidMenuSelectionException(char c) {
        super("Error: Invalid menu selection: " +c);
    }
    
    public String getErrorMessage() {
        return super.getMessage();
    }
}
