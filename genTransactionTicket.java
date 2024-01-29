
import java.util.Calendar;

public abstract class genTransactionTicket {
    protected int accountNum;
    protected Calendar dateOfTransaction;
    protected String typeOfTransaction;
    protected double amountofTransaction;
    protected int termOfCD;
    protected Calendar dateOfCheck;
    protected String socialSecurityNum;
    
    public genTransactionTicket() {
        accountNum = 0;
        Calendar calendar = Calendar.getInstance();
        dateOfTransaction = calendar;
        typeOfTransaction = "";
        amountofTransaction = 0;
        termOfCD = 0;
    }
    
    public genTransactionTicket(TransactionTicket tt) {
        accountNum = tt.getAccountNum();
        dateOfTransaction = tt.getDateOfTransaction();
        typeOfTransaction = tt.getTransactionType();
        amountofTransaction = tt.getTransactionAmount();
        termOfCD = tt.getTermofCD();
        dateOfCheck = tt.getCheckDate();
        socialSecurityNum = tt.getSSN();
    }
    
    public genTransactionTicket(int acct, Calendar date, String type, double amount, int term, Calendar chkDate, String ssn) {
        accountNum = acct;
        dateOfTransaction = date;
        typeOfTransaction = type;
        amountofTransaction = amount;
        termOfCD = term;
        dateOfCheck = chkDate;
        socialSecurityNum = ssn;
    }
    
    public genTransactionTicket(int acct, Calendar date, String type, double amount, int term) {
        accountNum = acct;
        dateOfTransaction = date;
        typeOfTransaction = type;
        amountofTransaction = amount;
        termOfCD = term;
    }
    
    public genTransactionTicket(int acct, Calendar date, String type, double amount, String ssn) {
        accountNum = acct;
        dateOfTransaction = date;
        typeOfTransaction = type;
        amountofTransaction = amount;
        socialSecurityNum = ssn;
    }
    
    public genTransactionTicket(int acct, Calendar date, String type, double amount) {
        accountNum = acct;
        dateOfTransaction = date;
        typeOfTransaction = type;
        amountofTransaction = amount;
    }
    
    public genTransactionTicket(int acct, Calendar date, String type, double amount, Calendar checkDate) {
        accountNum = acct;
        dateOfTransaction = date;
        typeOfTransaction = type;
        amountofTransaction = amount;
        dateOfCheck = checkDate;
    }
    
    public genTransactionTicket(int acct, Calendar date, String type) {
        accountNum = acct;
        dateOfTransaction = date;
        typeOfTransaction = type;
    }
    
    public genTransactionTicket(String ssn, Calendar date, String type) {
        socialSecurityNum = ssn;
        dateOfTransaction = date;
        typeOfTransaction = type;
    }
    
    public abstract TransactionTicket getTransactionTicketCopy();
    
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
    
    public abstract int getAccountNum();
    
    public abstract Calendar getDateOfTransaction();
    
    public abstract String getTransactionType();
    
    public abstract double getTransactionAmount();
    
    public abstract int getTermofCD();
    
    public abstract Calendar getCheckDate();
    
    public abstract String getSSN();
}
