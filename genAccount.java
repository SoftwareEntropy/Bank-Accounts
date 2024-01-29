
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class genAccount {
    protected Depositer depositer;
    protected int accountNum;
    protected String acctType;
    protected String acctStatus;
    protected double acctBalance;
    protected Calendar maturityDate;
    
    public genAccount() {
        depositer = new Depositer();
        accountNum = 000000;
        acctType = "";
        acctStatus = "Open";
        acctBalance = 0;
        Calendar calendar = Calendar.getInstance();
        maturityDate = calendar;
    }
    
    public genAccount(Account acct) {
        depositer = new Depositer (acct.depositer);
        accountNum = acct.accountNum;
        acctType = acct.acctType;
        acctStatus = acct.acctStatus;
        acctBalance = acct.acctBalance;
        maturityDate = acct.maturityDate;
    }

    public genAccount(Depositer dep, int acctNum, String type, double bal, Calendar calendar, String status) {
        depositer = dep;
        accountNum = acctNum;
        acctType = type;
        acctBalance = bal;
        maturityDate = calendar;
        acctStatus = status;
    }
    
    public genAccount(Depositer depositer, int acctNum, String type, double bal) {
        depositer = new Depositer();
        accountNum = acctNum;
        acctType = type;
        acctBalance = bal;
    }

    public abstract TransactionReceipt getBalance(TransactionTicket tt);
    
    public abstract TransactionReceipt makeDeposit(TransactionTicket tt);
    
    public abstract TransactionReceipt makeWithdrawal(TransactionTicket tt);
    
    public abstract TransactionReceipt clearCheck(TransactionTicket tt);
    
    public abstract TransactionReceipt closeAcct (TransactionTicket tt);
    
    public abstract TransactionReceipt reopenAcct (TransactionTicket tt);
    
    public abstract Account getAccountCopy();
    
    public abstract Depositer getDepositerCopy();
    
    public abstract boolean equals(Account acct);
    
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
    
    public abstract Depositer getDepositer();
    
    public abstract double getBalance();
    
    public abstract int getAcctNumber();
    
    public abstract String getAcctType();
    
    public abstract Calendar getMaturityDate();
    
    public abstract String getAccountStatus();
    
}
