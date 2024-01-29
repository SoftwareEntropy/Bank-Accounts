
import java.util.Calendar;

public class CheckTooOldException extends Exception {

    public CheckTooOldException() {
        super();
    }
    
    public CheckTooOldException(Calendar checkDate) {
        super("Error: Check is over 6 months old and cannot be cleared; [" +(checkDate.get(Calendar.MONTH)+ 1) +"/" +checkDate.get(Calendar.DATE) + "/" +checkDate.get(Calendar.YEAR)+"].");
    }
    
    public String getErrorMessage() {
        return super.getMessage();
    }
}
