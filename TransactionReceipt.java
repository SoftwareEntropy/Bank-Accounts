
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Calendar;

public class TransactionReceipt extends genTransactionReceipt{

    public TransactionReceipt() {
        super();
    }
    
    public TransactionReceipt(TransactionReceipt tr) {
        super(tr);
    }
    
    public TransactionReceipt(String flag, String reason, double pre, double post, Calendar date, int acctnum, TransactionTicket tt, String type) {
        super(flag, reason, pre, post, date, acctnum, tt, type);
    }
    
    public TransactionReceipt(String flag, String reason, double pre, double post, int acctnum, TransactionTicket tt, String type) {
        super(flag, reason, pre, post, acctnum, tt, type);
    }
    
    public TransactionReceipt(String flag, double pre, double post, Calendar date, int acctnum, TransactionTicket tt, String type) {
        super(flag, pre, post, date, acctnum, tt, type);
    }
    
    public TransactionReceipt(String flag, String reason, String ssn, TransactionTicket tt) {
        super(flag, reason, ssn, tt);
    }
    
    public TransactionReceipt (String flag, String reason, int acctnum, TransactionTicket tt) {
        super(flag, reason, acctnum, tt);
    }
    
    public TransactionReceipt(String flag, String reason, TransactionTicket tt) {
        super(flag, reason, tt);
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
                    + "\nSocial Security Number: %s\n", transactionType, successIndicatorFlag, reasonForFailure, tmonth, tday, tyear, socialSecurityNumber);
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
                            + "\nAccount Type: %s\n", tmonth, tday, tyear, transactionType, successIndicatorFlag, transactionAmount, pre, post, month, day, year, accountNumber, accountType);
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
                            + "\nAccount Type: %s\n", tmonth, tday, tyear, transactionType, successIndicatorFlag, reasonForFailure, transactionAmount, pre, post, month, day, year, accountNumber, accountType);
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
                            + "\nAccount Type: %s\n", tmonth, tday, tyear, transactionType, successIndicatorFlag, transactionAmount, pre, post, accountNumber, accountType);
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
                            + "\nAccount Type: %s\n", tmonth, tday, tyear, transactionType, successIndicatorFlag, reasonForFailure, transactionAmount, pre, post, accountNumber, accountType);
                    return str;
                }
            }
        }
    }
    
    public String toRecord () {
        String none = "";
        if (postTransactionMaturityDate == null) {
        String flag = String.format("%-50s", successIndicatorFlag.trim());
            if (reasonForFailure == null) {
                String reason = String.format("%-100s", none);
                String pre = String.format("%-20.2f", preTransactionBalance);
                String post = String.format("%-20.2f", postTransactionBalance);
                String acctNum = String.format("%-20d", accountNumber);
                String acctType = String.format("%-20s", accountType.trim());
                if (socialSecurityNumber == null) {
                    String ssn = String.format("%-20s", none);
                    String month1 = String.format("%-5s", none);
                    String day1 = String.format("%-5s", none);
                    String year1 = String.format("%-5s", none);
                    String record = "" + flag +reason + pre + post + acctNum + acctType + ssn + month1 + day1 + year1;
                    return record;
                } else {
                    String ssn = String.format("%-20s", socialSecurityNumber.trim());
                    String month1 = String.format("%-5s", none);
                    String day1 = String.format("%-5s", none);
                    String year1 = String.format("%-5s", none);
                    String record = "" + flag +reason + pre + post + acctNum + acctType + ssn + month1 + day1 + year1;
                    return record;
                }
            } else {
                String reason = String.format("%-100s", reasonForFailure.trim());
                String pre = String.format("%-20.2f", preTransactionBalance);
                String post = String.format("%-20.2f", postTransactionBalance);
                String acctNum = String.format("%-20d", accountNumber);
                String acctType = String.format("%-20s", accountType.trim());
                if (socialSecurityNumber == null) {
                    String ssn = String.format("%-20s", none);
                    String month1 = String.format("%-5s", none);
                    String day1 = String.format("%-5s", none);
                    String year1 = String.format("%-5s", none);
                    String record = "" + flag +reason + pre + post + acctNum + acctType + ssn + month1 + day1 + year1;
                    return record;
                } else {
                    String ssn = String.format("%-20s", socialSecurityNumber.trim());
                    String month1 = String.format("%-5s", none);
                    String day1 = String.format("%-5s", none);
                    String year1 = String.format("%-5s", none);
                    String record = "" + flag +reason + pre + post + acctNum + acctType + ssn + month1 + day1 + year1;
                    return record;
                }
            }
        } else {
            Calendar date = postTransactionMaturityDate;
            int month = date.get(Calendar.MONTH) + 1;
            int day = date.get(Calendar.DAY_OF_MONTH);
            int year = date.get(Calendar.YEAR);
            String flag = String.format("%-50s", successIndicatorFlag.trim());
            if (reasonForFailure == null) {
                String reason = String.format("%-100s", none);
                String pre = String.format("%-20.2f", preTransactionBalance);
                String post = String.format("%-20.2f", postTransactionBalance);
                String acctNum = String.format("%-20d", accountNumber);
                String acctType = String.format("%-20s", accountType.trim());
                if (socialSecurityNumber == null) {
                    String ssn = String.format("%-20s", none);
                    String month1 = String.format("%-5s", none);
                    String day1 = String.format("%-5s", none);
                    String year1 = String.format("%-5s", none);
                    String record = "" + flag +reason + pre + post + acctNum + acctType + ssn + month1 + day1 + year1;
                    return record;
                } else {
                    String ssn = String.format("%-20s", socialSecurityNumber.trim());
                    String month1 = String.format("%-5s", none);
                    String day1 = String.format("%-5s", none);
                    String year1 = String.format("%-5s", none);
                    String record = "" + flag +reason + pre + post + acctNum + acctType + ssn + month1 + day1 + year1;
                    return record;
                }
            } else {
                String reason = String.format("%-100s", reasonForFailure.trim());
                String pre = String.format("%-20.2f", preTransactionBalance);
                String post = String.format("%-20.2f", postTransactionBalance);
                String acctNum = String.format("%-20d", accountNumber);
                String acctType = String.format("%-20s", accountType.trim());
                if (socialSecurityNumber == null) {
                    String ssn = String.format("%-20s", none);
                    String month1 = String.format("%-5d", month);
                    String day1 = String.format("%-5d", day);
                    String year1 = String.format("%-5d", year);
                    String record = "" + flag +reason + pre + post + acctNum + acctType + ssn + month1 + day1 + year1;
                    return record;
                } else {
                    String ssn = String.format("%-20s", socialSecurityNumber.trim());
                    String month1 = String.format("%-5d", month);
                    String day1 = String.format("%-5d", day);
                    String year1 = String.format("%-5d", year);
                    String record = "" + flag +reason + pre + post + acctNum + acctType + ssn + month1 + day1 + year1;
                    return record;
                }
            }
        }
    }
    
    public static String reconstructReceipt(int accountNum, int index, TransactionTicket tt) throws Exception {
        try {
        String filename = ""+accountNum +".dat";
        RandomAccessFile file = new RandomAccessFile (""+filename, "rw");
        int bytes = 530;
        file.seek(index * bytes);
        String flag = "";
        for (int i=0; i<50; i++) {
            flag += file.readChar();
        }
        String flag1 = flag.trim();
        String reason = "";
        for (int i=0; i<100; i++) {
            reason += file.readChar();
        }
        String reason1 = reason.trim();
        String pre = "";
        for (int i=0; i<20; i++) {
            pre += file.readChar();
        }
        String pre1 = pre.trim();
        double pre2 = Double.parseDouble(pre1);
        String post = "";
        for (int i=0; i<20; i++) {
            post += file.readChar();
        }
        String post1 = post.trim();
        double post2 = Double.parseDouble(post1);
        String acctNum = "";
        for (int i=0; i<20; i++) {
            acctNum += file.readChar();
        }
        String acctNum1 = acctNum.trim();
        int acctNum2 = Integer.parseInt(acctNum1);
        String acctType = "";
        for (int i=0; i<20; i++) {
            acctType += file.readChar();
        }
        String acctType1 = acctType.trim();
        String ssn = "";
        for (int i=0; i<20; i++) {
            ssn += file.readChar();
        }
        String ssn1 = ssn.trim();
        String month = "";
        for (int i=0; i<5; i++) {
            month += file.readChar();
        }
        String month1 = month.trim();
        if (month1.equals("")) {
            String str = "\nTransaction: " + flag1 +"\nReason: " +reason1 +"\nPre-Transaction Balance: $" +pre2 +"\nPost-Transaction Balance: $" +post2 +"\nAccount number: " +acctNum2 + "\nAccount Type: " +acctType1;
            return str;
        }
        int month2 = Integer.parseInt(month1) -1;
        String day = "";
        for (int i=0; i<5; i++) {
            day += file.readChar();
        }
        String day1 = day.trim();
        int day2 = Integer.parseInt(day1);
        String year = "";
        for (int i=0; i<5; i++) {
            year += file.readChar();
        }
        String year1 = year.trim();
        int year2 = Integer.parseInt(year1);
        Calendar maturityDate = Calendar.getInstance();
        maturityDate.set(year2, month2, day2);
        String str = "\nTransaction: " + flag1 +"\nReason: " +reason1 +"\nPre-Transaction Balance: $" +pre2 +"\nPost-Transaction Balance: $" +post2 +"\nAccount number: " +acctNum2 + "\nAccount Type: " +acctType1 +"\nMaturity Date: " +month2 +"/" +day2 +"/" +year2;
            return str;
        }
        catch (NullPointerException ex) {
            return null;
        }
    }
    
    public TransactionReceipt getTransactionReceiptCopy() {
        TransactionReceipt tr = new TransactionReceipt(successIndicatorFlag, reasonForFailure, preTransactionBalance, postTransactionBalance, postTransactionMaturityDate, accountNumber, transactionTicket, accountType);
        return tr;
    }
    
    public TransactionReceipt getTransactionReceiptCopyForHistory() {
        TransactionReceipt tr = new TransactionReceipt(successIndicatorFlag, reasonForFailure, socialSecurityNumber, transactionTicket);
        return tr;
    }
    
    public TransactionTicket getTransactionTicket() {
        return transactionTicket;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    public String getTransactionSuccessIndicatorFlag() {
        return successIndicatorFlag;
    }
    
    public String getTransactionFailureReason() {
        return reasonForFailure;
    }
    
    public double getPreTransactionBalance() {
        return preTransactionBalance;
    }
    
    public double getPostTransactionBalance() {
        return postTransactionBalance;
    }
    
    public Calendar getPostTransactionMaturityDate() {
        return postTransactionMaturityDate;
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
    
}
