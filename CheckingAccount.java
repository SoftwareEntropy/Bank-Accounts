
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckingAccount extends Account{
    
    
    public CheckingAccount() {
        super();
    }
    
    public CheckingAccount (Account acct) {
        super(acct);
    }
    
    /* Method getAccountCopy()
    *   Input: None
    *   Process: Overrides method in Account superclass
    *            Creates a new CheckingAccount reference object
    *   Output: returns an CheckingAccount subclass reference object
    */
    @Override
    public CheckingAccount getAccountCopy() {
        Account copy = new Account (depositer, accountNum, acctType, acctBalance, maturityDate, acctStatus);
        CheckingAccount checkingCopy = new CheckingAccount(copy);
        return checkingCopy;
    }
    
    /* Method makeDeposit()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Performs deposit and prints new balance, then creates a TransactionReceipt object with relevant information
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    @Override
    public TransactionReceipt makeDeposit(TransactionTicket tt) {
        String flag = "Deposit success";
        String reason = "Default";
        Double preBal = acctBalance;
        double reqDeposit = tt.getTransactionAmount();
        acctBalance = acctBalance + reqDeposit;
        TransactionReceipt tr = new TransactionReceipt(flag, reason, preBal, acctBalance, accountNum, tt, acctType);
        try {
            Bank.addTransaction(tr);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        try {
            Bank.updateAccountInfo(tt.getAccountNum(), toRecord());
        } catch (IOException ex) {
            System.out.println(ex);
        }
        Bank.addCheckingTotal(reqDeposit);
        Bank.addAccountTotal(reqDeposit);
        return tr;
    }
    
    /* Method makeWithdrawal()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Performs withdrawal and prints new balance, then creates a TransactionReceipt object with relevant information
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    @Override
    public TransactionReceipt makeWithdrawal(TransactionTicket tt) {
        String flag = "Withdrawal success";
        String reason = "Default";
        Double preBal = acctBalance;
        double reqWithdrawal = tt.getTransactionAmount();
        acctBalance = acctBalance - reqWithdrawal;
        TransactionReceipt tr = new TransactionReceipt(flag, reason, preBal, acctBalance, accountNum, tt, acctType);
        try {
            Bank.addTransaction(tr);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        try {
            Bank.updateAccountInfo(tt.getAccountNum(), toRecord());
        } catch (IOException ex) {
            System.out.println(ex);
        }
        Bank.subtractCheckingTotal(reqWithdrawal);
        Bank.subtractAccountTotal(reqWithdrawal);
        return tr;
    }
    
    /* Method clearCheck()
    *   Input: bank - Object of the Bank class
    *          tt - reference to the TransactionTicket object
    *   Process: Performs withdrawal and prints new balance if account balance allows, then creates a TransactionReceipt object with relevant information
    *            If account balance is insufficient, applies a $2.50 penalty to the balance
    *            Creates and adds a TransactionReceipt object to the arraylist
    *   Output: returns a TransactionReceipt object
    */
    @Override
    public TransactionReceipt clearCheck(TransactionTicket tt) {
        double checkAmount = tt.getTransactionAmount();
        double preBal = acctBalance;
        try {
        if (acctBalance >= checkAmount) {
            if (acctBalance >= 2500) {
                String flag = "Clear check success";
                String reason = "Default";
                acctBalance = acctBalance - checkAmount;
                TransactionReceipt tr = new TransactionReceipt(flag, reason, preBal, acctBalance, accountNum, tt, acctType);
                Bank.addTransaction(tr);
                Bank.subtractCheckingTotal(checkAmount);
                Bank.subtractAccountTotal(checkAmount);
                try {
                    Bank.updateAccountInfo(tt.getAccountNum(), toRecord());
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                return tr;
            }
            else if (acctBalance < 2500 && acctBalance >= (checkAmount + 1.50)) {
                String flag = "Clear check success (Low balance fee $1.50 applied)";
                String reason = "Default";
                acctBalance = acctBalance - checkAmount - 1.50;
                //Account balance below $2500.00; Additional fee of $1.50 has been applied
                TransactionReceipt tr = new TransactionReceipt(flag, reason, preBal, acctBalance, accountNum, tt, acctType);
                Bank.addTransaction(tr);
                Bank.subtractCheckingTotal(checkAmount + 1.50);
                Bank.subtractAccountTotal(checkAmount + 1.50);
                try {
                    Bank.updateAccountInfo(tt.getAccountNum(), toRecord());
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                return tr;
            }
            else {
                String flag = "Clear check error";
                String reason = "Insufficient funds to pay low balance fee ($1.50) to clear check";
                System.out.println("Error: Insufficient funds in account to pay low balance fee ($1.50) to clear check");
                double penalty = 2.50;
                acctBalance = acctBalance - penalty;
                //Low funds penalty (-$2.50) applied
                TransactionReceipt tr = new TransactionReceipt(flag, reason, preBal, acctBalance, accountNum, tt, acctType);
                Bank.addTransaction(tr);
                Bank.subtractCheckingTotal(penalty);
                Bank.subtractAccountTotal(penalty);
                try {
                    Bank.updateAccountInfo(tt.getAccountNum(), toRecord());
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                return tr;
            }
        } else {
            //Error: Insufficient funds in account to clear check; $2.50 Penalty applied
            double penalty = 2.50;
            acctBalance = acctBalance - penalty;
            Bank.subtractCheckingTotal(penalty);
            Bank.subtractAccountTotal(penalty);
            try {
                Bank.updateAccountInfo(tt.getAccountNum(), toRecord());
            } catch (IOException ex) {
                System.out.println(ex);
            }
            throw new InsufficientFundsException (checkAmount, acctBalance);
        }
    }
        catch (InsufficientFundsException e) {
            String flag = "Clear check error";
            String reason = "Insufficient funds to clear check, $2.50 penalty applied";
            TransactionReceipt tr = new TransactionReceipt(flag, reason, preBal, acctBalance, accountNum, tt, acctType);
            try {
                Bank.updateAccountInfo(tt.getAccountNum(), toRecord());
            } catch (IOException ex) {
                System.out.println(ex);
            }
            return tr;
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    //public void addTransaction (TransactionReceipt tr) {
    //    transactionReceipt.add(tr);
    //}
    
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
        String str = String.format("%-20s %-20s %-20s %-20d %-20s %-20s %-20.2f %-5d %-5d %-5d\n", name.getLastName(), name.getFirstName(), dep.getSSN(), accountNum, acctType, acctStatus, acctBalance, month, day, year);
        return str;
    }
    
    
    public String toRecord () {
        Depositer dep = depositer.getDepositerCopy();
        Name name = dep.getNameCopy();
        String lastName = String.format("%-20s ", name.getLastName().trim());
        String firstName = String.format("%-20s ", name.getFirstName().trim());
        String ssn = String.format("%-20s ", dep.getSSN().trim());
        String acctNum = String.format("%-20d ", accountNum);
        String acctType1 = String.format("%-20s ", acctType.trim());
        String acctStatus1 = String.format("%-20s ", acctStatus.trim());
        String acctBalance1 = String.format("%-20.2f ", acctBalance);
        Calendar date = maturityDate;
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int year = date.get(Calendar.YEAR);
        String month1 = String.format("%-5d ", month);
        String day1 = String.format("%-5d ", day);
        String year1 = String.format("%-5d\n", year);
        String record = "" + lastName + firstName + ssn + acctNum + acctType1 + acctStatus1 + acctBalance1 + month1 + day1 + year1;
        return record;
    }
}
