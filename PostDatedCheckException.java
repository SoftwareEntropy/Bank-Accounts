
import java.util.Calendar;

public class PostDatedCheckException extends Exception {
    
    public PostDatedCheckException() {
        super();
    }
    
    public PostDatedCheckException(Calendar checkDate) {
        super("Error: Check is post-dated and cannot be cleared; [" +(checkDate.get(Calendar.MONTH)+ 1) +"/" +checkDate.get(Calendar.DATE) + "/" +checkDate.get(Calendar.YEAR)+"].");
    }
    
    public String getErrorMessage() {
        return super.getMessage();
    }
}
