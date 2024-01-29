
import java.util.Calendar;

public class TransactionTicket extends genTransactionTicket{    
    
    public TransactionTicket() {
        super();
    }
    
    public TransactionTicket(TransactionTicket tt) {
        super(tt);
    }
    
    public TransactionTicket(int acct, Calendar date, String type, double amount, int term, Calendar chkDate, String ssn) {
        super(acct, date, type, amount, term, chkDate, ssn);
    }
    
    public TransactionTicket(int acct, Calendar date, String type, double amount, int term) {
        super(acct, date, type, amount, term);
    }
    
    public TransactionTicket(int acct, Calendar date, String type, double amount, String ssn) {
        super(acct, date, type, amount, ssn);
    }
    
    public TransactionTicket(int acct, Calendar date, String type, double amount) {
        super(acct, date, type, amount);
    }
    
    public TransactionTicket(int acct, Calendar date, String type, double amount, Calendar checkDate) {
        super(acct, date, type, amount, checkDate);
    }
    
    public TransactionTicket(int acct, Calendar date, String type) {
        super(acct, date, type);
    }
    
    public TransactionTicket(String ssn, Calendar date, String type) {
        super(ssn, date, type);
    }

    public TransactionTicket getTransactionTicketCopy() {
        TransactionTicket copy = new TransactionTicket (accountNum, dateOfTransaction, typeOfTransaction, amountofTransaction, termOfCD, dateOfCheck, socialSecurityNum);
        return copy;
    }
    
    public String toString() {
        Calendar tdate = dateOfTransaction;
        int tmonth = tdate.get(Calendar.MONTH) + 1;
        int tday = tdate.get(Calendar.DAY_OF_MONTH);
        int tyear = tdate.get(Calendar.YEAR);
        if (termOfCD > 0) {
            String str = String.format("%-20d %-15d/%-1d/%-4d %-20s %-20s %-20d %-20s", accountNum, tmonth, tday, tyear, typeOfTransaction, amountofTransaction, termOfCD, socialSecurityNum);
            return str;
        } else if (typeOfTransaction.equals("Clear Check")) {
            Calendar date = dateOfCheck;
            int month = date.get(Calendar.MONTH) + 1;
            int day = date.get(Calendar.DAY_OF_MONTH);
            int year = date.get(Calendar.YEAR);
            String str = String.format("%-20d %-15d/%-1d/%-4d %-20s %-20s %-15d/%-1d/%-4d %-20s", accountNum, tmonth, tday, tyear, typeOfTransaction, amountofTransaction, month, day, year, socialSecurityNum);
            return str;
        } else {
            String str = String.format("%-20d %-15d/%-1d/%-4d %-20s %-20s %-40s", accountNum, tmonth, tday, tyear, typeOfTransaction, amountofTransaction, socialSecurityNum);
            return str;
        }
    }
    
    public int getAccountNum() {
        return accountNum;
    }
    
    public Calendar getDateOfTransaction() {
        return dateOfTransaction;
    }
    
    public String getTransactionType() {
        return typeOfTransaction;
    }
    
    public double getTransactionAmount() {
        return amountofTransaction;
    }
    
    public int getTermofCD() {
        return termOfCD;
    }
    
    public Calendar getCheckDate() {
        return dateOfCheck;
    }
    
    public String getSSN() {
        return socialSecurityNum;
    }
    
}
