
import java.text.DecimalFormat;
import java.util.Calendar;

public abstract class genTransactionReceipt {
    protected TransactionTicket transactionTicket;
    protected String successIndicatorFlag;
    protected String reasonForFailure;
    protected double preTransactionBalance;
    protected double postTransactionBalance;
    protected Calendar postTransactionMaturityDate;
    protected int accountNumber;
    protected String accountType;
    protected String socialSecurityNumber;
    
    public genTransactionReceipt() {
        successIndicatorFlag = "Error";
        reasonForFailure = "Default";
        preTransactionBalance = 0;
        postTransactionBalance = 0;
        Calendar calendar = Calendar.getInstance();
        postTransactionMaturityDate = calendar;
        accountNumber = 000000;
        accountType = "Default";
    }
    
    public genTransactionReceipt(TransactionReceipt tr) {
        transactionTicket = tr.transactionTicket;
        successIndicatorFlag = tr.successIndicatorFlag;
        reasonForFailure = tr.reasonForFailure;
        preTransactionBalance = tr.preTransactionBalance;
        postTransactionBalance = tr.postTransactionBalance;
        postTransactionMaturityDate = tr.postTransactionMaturityDate;
        accountNumber = tr.accountNumber;
        accountType = tr.accountType;
    }
    
    public genTransactionReceipt(String flag, String reason, double pre, double post, Calendar date, int acctnum, TransactionTicket tt, String type) {
        successIndicatorFlag = flag;
        reasonForFailure = reason;
        preTransactionBalance = pre;
        postTransactionBalance = post;
        postTransactionMaturityDate = date;
        accountNumber = acctnum;
        transactionTicket = tt;
        accountType = type;
    }
    
    public genTransactionReceipt(String flag, String reason, double pre, double post, int acctnum, TransactionTicket tt, String type) {
        successIndicatorFlag = flag;
        reasonForFailure = reason;
        preTransactionBalance = pre;
        postTransactionBalance = post;
        accountNumber = acctnum;
        transactionTicket = tt;
        accountType = type;
    }
    
    public genTransactionReceipt(String flag, double pre, double post, Calendar date, int acctnum, TransactionTicket tt, String type) {
        successIndicatorFlag = flag;
        preTransactionBalance = pre;
        postTransactionBalance = post;
        postTransactionMaturityDate = date;
        accountNumber = acctnum;
        transactionTicket = tt;
        accountType = type;
    }
    
    public genTransactionReceipt(String flag, String reason, String ssn, TransactionTicket tt) {
        successIndicatorFlag = flag;
        reasonForFailure = reason;
        socialSecurityNumber = ssn;
        transactionTicket = tt;
    }
    
    public genTransactionReceipt (String flag, String reason, int acctnum, TransactionTicket tt) {
        successIndicatorFlag = flag;
        reasonForFailure = reason;
        accountNumber = acctnum;
        transactionTicket = tt;

    }
    
    public genTransactionReceipt(String flag, String reason, TransactionTicket tt) {
        successIndicatorFlag = flag;
        reasonForFailure = reason;
        transactionTicket = tt;
    }
    
    public String toString() {
        DecimalFormat d = new DecimalFormat("$#,##0.00");
        String pre = d.format(preTransactionBalance);
        String post = d.format(postTransactionBalance);
        String transactionAmount = d.format(transactionTicket.getTransactionAmount());
        String transactionType = transactionTicket.getTransactionType();
        Calendar tdate = transactionTicket.getDateOfTransaction();
            int tmonth = tdate.get(Calendar.MONTH) + 1;
            int tday = tdate.get(Calendar.DAY_OF_MONTH);
            int tyear = tdate.get(Calendar.YEAR);
        if (transactionType.contains("Account info")) {
            String str = String.format("\n\nTransaction Type: %s"
                    + "\nTransaction Result: %s"
                    + "\nReason: %s"
                    + "\nDate of Transaction: %d/%d/%d"
                    + "\nSocial Security Number: %s", transactionType, successIndicatorFlag, reasonForFailure, tmonth, tday, tyear, socialSecurityNumber);
            return str;
        } else {
            if (successIndicatorFlag.contains("CD")) {
                Calendar date = postTransactionMaturityDate;
                int month = date.get(Calendar.MONTH) + 1;
                int day = date.get(Calendar.DAY_OF_MONTH);
                int year = date.get(Calendar.YEAR);
                if (!successIndicatorFlag.contains("error")) {
                    String str = String.format("\n\nTransaction Date: %d/%d/%d"
                            + "\nTransaction Type: %s"
                            + "\nTransaction Result: %s"
                            + "\nTransaction Amount: %s"
                            + "\nPre-Transaction Balance: %s"
                            + "\nPost-Transaction Balance: %s"
                            + "\nMaturity Date: %d/%d/%d"
                            + "\nAccount Number: %d"
                            + "\nAccount Type: %s", tmonth, tday, tyear, transactionType, successIndicatorFlag, transactionAmount, pre, post, month, day, year, accountNumber, accountType);
                    return str;
                } else {
                    String str = String.format("\n\nTransaction Date: %d/%d/%d"
                            + "\nTransaction Type: %s"
                            + "\nTransaction Result: %s"
                            + "\nReason for Failure: %s"
                            + "\nTransaction Amount: %s"
                            + "\nPre-Transaction Balance: %s"
                            + "\nPost-Transaction Balance: %s"
                            + "\nMaturity Date: %d/%d/%d"
                            + "\nAccount Number: %d"
                            + "\nAccount Type: %s", tmonth, tday, tyear, transactionType, successIndicatorFlag, reasonForFailure, transactionAmount, pre, post, month, day, year, accountNumber, accountType);
                    return str;
                }
            } else {
                if (!successIndicatorFlag.contains("error")) {
                    String str = String.format("\nTransaction Date: %d/%d/%d"
                            + "\nTransaction Type: %s"
                            + "\nTransaction Result: %s"
                            + "\nTransaction Amount: %s"
                            + "\nPre-Transaction Balance: %s"
                            + "\nPost-Transaction Balance: %s"
                            + "\nAccount Number: %d"
                            + "\nAccount Type: %s", tmonth, tday, tyear, transactionType, successIndicatorFlag, transactionAmount, pre, post, accountNumber, accountType);
                    return str;
                } else {
                    String str = String.format("\nTransaction Date: %d/%d/%d"
                            + "\nTransaction Type: %s"
                            + "\nTransaction Result: %s"
                            + "\nReason for Failure: %s"
                            + "\nTransaction Amount: %s"
                            + "\nPre-Transaction Balance: %s"
                            + "\nPost-Transaction Balance: %s"
                            + "\nAccount Number: %d"
                            + "\nAccount Type: %s", tmonth, tday, tyear, transactionType, successIndicatorFlag, reasonForFailure, transactionAmount, pre, post, accountNumber, accountType);
                    return str;
                }
            }
        }
    }
    
    public abstract TransactionReceipt getTransactionReceiptCopy();
    
    public abstract TransactionReceipt getTransactionReceiptCopyForHistory();
    
    public abstract TransactionTicket getTransactionTicket();
    
    public abstract String getAccountType();
    
    public abstract String getTransactionSuccessIndicatorFlag();
    
    public abstract String getTransactionFailureReason();
    
    public abstract double getPreTransactionBalance();
    
    public abstract double getPostTransactionBalance();
    
    public abstract Calendar getPostTransactionMaturityDate();
    
    public abstract int getAccountNumber();
    
}
