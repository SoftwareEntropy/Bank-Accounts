
import java.text.DecimalFormat;
import java.util.Calendar;

public class Check {
    private int accountNum;
    private double checkAmount;
    private Calendar dateOfCheck;
    
    public Check() {
        accountNum = 000000;
        checkAmount = 0;
        Calendar calendar = Calendar.getInstance();
        dateOfCheck = calendar;
    }
    
    public Check(Check c) {
        accountNum = c.accountNum;
        checkAmount = c.checkAmount;
        dateOfCheck = c.dateOfCheck;
    }
    
    public Check (int acct, double check, Calendar date) {
        accountNum = acct;
        checkAmount = check;
        dateOfCheck = date;
    }
    
    public Check getCheckCopy() {
        Check copy = new Check(accountNum, checkAmount, dateOfCheck);
        return copy;
    }
    
    public String toString() {
        DecimalFormat d = new DecimalFormat("$#,##0.00");
        String check = d.format(checkAmount);
        Calendar date = dateOfCheck;
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int year = date.get(Calendar.YEAR);
        String str = String.format("\nAccount Number: %s"
                + "\nCheck Amount: %s"
                + "\nCheck Date: %d/%d/%d", accountNum, check, month, day, year);
        return str;
    }
    
    public double getCheckAmount() {
        return checkAmount;
    }
    
    public int getAccountNum() {
        return accountNum;
    }
    
    public Calendar getDateOfCheck() {
        return dateOfCheck;
    }
    
}
