
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Account extends genAccount{
    
    public Account() {
        super();
    }
    
    public Account(Account acct) {
        super(acct);
    }

    public Account(Depositer dep, int acctNum, String type, double bal, Calendar calendar, String status) {
        super(dep, acctNum, type, bal, calendar, status);
    }
    
    public Account(Depositer depositer, int acctNum, String type, double bal) {
        super(depositer, acctNum, type, bal);
    }


    /* Method getBalance()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Finds and prints account balance, then creates a TransactionReceipt object
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    public TransactionReceipt getBalance(TransactionTicket tt) {
        String flag = "Get Balance success";
        String reason = "Default";
        Double preBal = acctBalance;
        Calendar date = maturityDate;
        TransactionReceipt tr = new TransactionReceipt(flag, reason, preBal, acctBalance, date, accountNum, tt, acctType);
        try {
            Bank.addTransaction(tr);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return tr;
    }
    
    /* Method makeDeposit()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Method overridden in SavingsAccount, CheckingAccount, and CDAccount subclasses
    *            Performs deposit and prints new balance, then creates a TransactionReceipt object with relevant information
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    public TransactionReceipt makeDeposit(TransactionTicket tt) {
       return null;
    }
    
    /* Method makeWithdrawal()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Method overridden in SavingsAccount, CheckingAccount, and CDAccount subclasses
    *            Performs withdrawal and prints new balance, then creates a TransactionReceipt object with relevant information
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    public TransactionReceipt makeWithdrawal(TransactionTicket tt) {
        return null;
    }
    
    /* Method clearCheck()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Method overridden in CheckingAccount subclass
    *            Performs withdrawal and prints new balance if account balance allows, then creates a TransactionReceipt object with relevant information
    *            If account balance is insufficient, applies a $2.50 penalty to the balance
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    public TransactionReceipt clearCheck(TransactionTicket tt) {
        return null;
    }
    
    /* Method closeAcct()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Changes account status to "Closed"
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    public TransactionReceipt closeAcct (TransactionTicket tt) {
        String flag = "Close account success";
        double balance = acctBalance;
        Calendar matDate = maturityDate;
        acctStatus = "Closed";
        TransactionReceipt tr = new TransactionReceipt(flag, balance, balance, matDate, accountNum, tt, acctType);
        try {
            Bank.addTransaction(tr);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return tr;
    }
    
    /* Method reopenAcct()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Changes account status to "Open"
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    public TransactionReceipt reopenAcct (TransactionTicket tt) {
        String flag = "Reopen account success";
        double balance = acctBalance;
        Calendar matDate = maturityDate;
        acctStatus = "Open";
        TransactionReceipt tr = new TransactionReceipt(flag, balance, balance, matDate, accountNum, tt, acctType);
        try {
            Bank.addTransaction(tr);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return tr;
    }
    
    /* Method getTransactionHistory()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Retrives TransactionReceipt information from the arraylist
    *            Creates a TransactionReceipt object
    *   Output: returns a TransactionReceipt object
    */
    /*public ArrayList <TransactionReceipt> getTransactionHistory (TransactionTicket tt) {
        ArrayList <TransactionReceipt> history = new ArrayList<>();
        int numReceipts = getTransactionReceiptSize();
        for (int i=0; i<numReceipts; i++) {
            TransactionReceipt tr = getTransactionReceipt(i);
            history.add(i, tr);
        }
        return history;
    }*/
    
    /* Method getAccountCopy()
    *   Input: None
    *   Process: Methods overridden in SavingsAccount, CheckingAccount, and CDAccount subclasses
    *            Creates a new account object and then uses it to instantiate a new subclass object
    *   Output: returns an appropriate Account subclass object
    */
    public Account getAccountCopy() {
        return null;
    }
    
    public Depositer getDepositerCopy() {
        Depositer copy = new Depositer (depositer);
        return copy;
    }
    
    //public int getTransactionReceiptSize() {
    //    return transactionReceipt.size();
    //}
    
    public boolean equals(Account acct) {
        if (depositer.equals(acct.depositer) && accountNum == acct.accountNum && acctType.equals(acct.acctType) && acctStatus.equals(acct.acctStatus) && acctBalance == acct.acctBalance && maturityDate.equals(acct.maturityDate)) {
            return true;
        } else {
            return false;
        }
    }
    
    public String toString() {
        Depositer dep = depositer.getDepositerCopy();
        Name name = dep.getNameCopy();
        DecimalFormat d = new DecimalFormat("$#,##0.00");
        String balance = d.format(acctBalance);
        if (acctType.equals("CD")) {
            Calendar date = maturityDate;
            int month = date.get(Calendar.MONTH) + 1;
            int day = date.get(Calendar.DAY_OF_MONTH);
            int year = date.get(Calendar.YEAR);
            String str = String.format("\n%-18s"+ "%-18s"+ "%-18s"+ "%-18d"+ "%-18s"+ "%-15s"+ "%15s"+ "%12d/%1d/%4d", name.getLastName(), name.getFirstName(), dep.getSSN(), accountNum, acctType, acctStatus, balance, month, day, year);
            return str;
        } else {
            String str = String.format("\n%-18s"+ "%-18s"+ "%-18s"+ "%-18d"+ "%-18s"+ "%-15s"+ "%15s", name.getLastName(), name.getFirstName(), dep.getSSN(), accountNum, acctType, acctStatus, balance);
            return str;
        }
    }
    
    public String getAccountInfo() {
        Depositer dep = depositer.getDepositerCopy();
        Name name = dep.getNameCopy();
        Calendar date = maturityDate;
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int year = date.get(Calendar.YEAR);
        String str = String.format("%-20s%-20s%-20s%-20d%-20s%-20s%-20.2f%-5d%-5d%-5d\n", name.getLastName(), name.getFirstName(), dep.getSSN(), accountNum, acctType, acctStatus, acctBalance, month, day, year);
        return str;
    }
    
    public String toRecord () {
        Depositer dep = depositer.getDepositerCopy();
        Name name = dep.getNameCopy();
        Calendar date = maturityDate;
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int year = date.get(Calendar.YEAR);
        String lastName = String.format("%-20s ", name.getLastName().trim());
        String firstName = String.format("%-20s ", name.getFirstName().trim());
        String ssn = String.format("%-20s ", dep.getSSN().trim());
        String acctNum = String.format("%-20d ", accountNum);
        String acctType1 = String.format("%-20s ", acctType);
        String acctStatus1 = String.format("%-20s ", acctStatus);
        String acctBalance1 = String.format("%-20.2f ", acctBalance);
        String month1 = String.format("%-5d ", month);
        String day1 = String.format("%-5d ", day);
        String year1 = String.format("%-5d\n", year);
        String record = "" + lastName + firstName + ssn + acctNum + acctType1 + acctStatus1 + acctBalance1 + month1 + day1 + year1;
        return record;
    }
    
    public Depositer getDepositer() {
        return depositer;
    }
    
    public double getBalance() {
        return acctBalance;
    }
    
    public int getAcctNumber() {
        return accountNum;
    }
    
    public String getAcctType() {
        return acctType;
    }
    
    public Calendar getMaturityDate() {
        return maturityDate;
    }
    
    public String getAccountStatus() {
        return acctStatus;
    }
}
