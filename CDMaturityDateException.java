
import java.util.Calendar;

public class CDMaturityDateException extends Exception {

    public CDMaturityDateException() {
        super();
    }
    
    public CDMaturityDateException(Calendar maturityDate) {
        super("Error: Maturity Date not reached; [" +(maturityDate.get(Calendar.MONTH)+ 1) +"/" +maturityDate.get(Calendar.DATE) + "/" +maturityDate.get(Calendar.YEAR)+"].");
    }
    
    public String getErrorMessage() {
        return super.getMessage();
    }
    
}
