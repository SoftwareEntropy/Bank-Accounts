
import java.io.*;
import java.util.*;

public class Bank {
    //private ArrayList<Account> account;
    static RandomAccessFile account;
    private static double totalAmountinSavingsAccts;
    private static double totalAmountinCheckingAccts;
    private static double totalAmountinCDAccts;
    private static double totalAmountinAllAccts;
    public static int numberOfAccounts;
    
    public Bank() throws Exception{
        //account = new ArrayList<>();
        account = new RandomAccessFile("BankAccounts.dat", "rw");
        account.setLength(0);
        totalAmountinSavingsAccts = 0;
        totalAmountinCheckingAccts = 0;
        totalAmountinCDAccts = 0;
        totalAmountinAllAccts = 0;
    }
    
    /* Method findAcct()
    *   Input: bank - Object of the Bank class
    *          num_accts - reference to the number of accounts in the Account arraylist
    *          reqAccount - reference to the account number that is being searched for
    *   Process: Uses a 'for' loop to perform a sequential search of the array for a matching account number
    *   Output: If the loop finds a matching account number, returns the array index, otherwise returns -1
    */
    /*private int findAcct(int num_accts, int reqAccount) {
        for (int i=0; i<num_accts; i++) {
            Account acct = getAcctCopy(i);
            int acctNumber = acct.getAcctNumber();
            if (reqAccount == acctNumber) {
                return i;
            }
        }
        return -1;
    }*/    
    
    /* Method findAcct()
    *   Input: reqAccount - Account number to search for
    *   Process: Uses a sequential search to find a matching account number
    *   Output: Returns an index of the account information location
    */
    public static int findAcct(int reqAccount) throws IOException {
        int bytesPerChar = 2;
        int charsPerString = 165;
        int interval = bytesPerChar * charsPerString;
        for (int i=0; i<numberOfAccounts; i++) {
            account = new RandomAccessFile("BankAccounts.dat", "rw");
            account.seek(interval * i + 120);
            String s = "";
            for (int j=0; j<20; j++) {
                s += (account.readChar());
            }
            String d = s.trim();
            int found = Integer.parseInt(d);
            if (reqAccount == found) {
                return (i);
            }
        }
        return -1;
    }
    
    /* Method findAcctSSN()
    *   Input: ssn - Social security number to search for
    *   Process: Uses a sequential search to find a matching social security number
    *   Output: Returns an index of the account information location
    */
    public static int findAcctSSN(String ssn) throws IOException {
        int bytesPerChar = 2;
        int charsPerString = 165;
        int interval = bytesPerChar * charsPerString;
        for (int i=0; i<numberOfAccounts; i++) {
            account = new RandomAccessFile("BankAccounts.dat", "rw");
            account.seek(interval * i + 80);
            String s = "";
            for (int j=0; j<20; j++) {
                s += (account.readChar());
            }
            String found = s.trim();
            if (ssn.equals(found)) {
                return (i);
            }
        }
        return -1;
    }
    
    /* Method getAccountInfo()
    *   Input: index - Returns a string of account information at the index
    *   Process: Creates a string of account information at the index
    *   Output: Returns a string of account information
    */
    public String getAccountInfo (int index) throws IOException {
        int bytesPerChar = 2;
        int charsPerString = 165;
        int interval = bytesPerChar * charsPerString ;
        int search = index * interval;
        account = new RandomAccessFile("BankAccounts.dat", "rw");
        account.seek(search);
        String str = "";
        for (int i=0; i<165; i++) {
            str += account.readChar();
        }
        return str;
    }
    
    /* Method updateAccountInfo()
    *   Input: accountNumber - Account number
    *          update - New account information
    *   Process: Uses a sequential search to find a matching account number
    *            Moves file pointer to account location, overwrites data with new data
    *   Output: None
    */
    public static void updateAccountInfo (int accountNumber, String update) throws FileNotFoundException, IOException {
        int index = findAcct(accountNumber);
        account.seek(330 * index);
        account.writeChars(update);
    }
    
    /* Method addTransaction()
    *   Input: tr - TransactionReceipt object
    *   Process: Uses transactionreceipt information to find and/or create account file, writes transaction receipt information to it
    *   Output: None
    */
    public static void addTransaction(TransactionReceipt tr) throws FileNotFoundException, IOException {
        int acctNum = tr.getAccountNumber();
        String acct = acctNum+".dat";
        RandomAccessFile file = new RandomAccessFile(""+acct, "rw");
        file.seek(file.length());
        file.writeChars(tr.toRecord());
        file.close();
    }
    
    /* Method reconstructAccount()
    *   Input: index - Account index
    *   Process: Obtains account information from file, uses it to make an account object
    *   Output: Returns an account object reference
    */
    public Account reconstructAccount(int index) throws IOException {
        try {
            account.seek(index * 330);
            String lastName = "";
            for (int i=0; i<20; i++) {
                lastName += account.readChar();
            }
            String lastName2 = lastName.trim();
            String firstName = "";
            for (int i=0; i<20; i++) {
                firstName += account.readChar();
            }
            String firstName2 = firstName.trim();
            String ssn = "";
            for (int i=0; i<20; i++) {
                ssn += account.readChar();
            }
            String ssn2 = ssn.trim();
            String acctNum = "";
            for (int i=0; i<20; i++) {
                acctNum += account.readChar();
            }
            String acctNum2 = acctNum.trim();
            int acctNum3 = Integer.parseInt(acctNum2);
            String acctType = "";
            for (int i=0; i<20; i++) {
                acctType += account.readChar();
            }
            String acctType2 = acctType.trim();
            String acctStatus = "";
            for (int i=0; i<20; i++) {
                acctStatus += account.readChar();
            }
            String acctStatus2 = acctStatus.trim();
            String balance = "";
            for (int i=0; i<25; i++) {
                balance += account.readChar();
            }
            String balance2 = balance.trim();
            double balance3 = Double.parseDouble(balance2);
            if (acctType2.equals("CD")) {
                String month = "";
                for (int i=0; i<5; i++) {
                    month += account.readChar();
                }
                String month2 = month.trim();
                int maturityMonth = Integer.parseInt(month2) - 1;
                String date = "";
                for (int i=0; i<5; i++) {
                    date += account.readChar();
                }
                String date2 = date.trim();
                int maturityDate = Integer.parseInt(date2);
                String year = "";
                for (int i=0; i<10; i++) {
                    year += account.readChar();
                }
                String year2 = year.trim();
                int maturityYear = Integer.parseInt(year2);
                Calendar maturity = Calendar.getInstance();
                maturity.set(maturityYear, maturityMonth, maturityDate);
                Name name = new Name (firstName2, lastName2);
                Depositer depositer = new Depositer (name, ssn2);
                Account acct = new Account(depositer, acctNum3, acctType2, balance3, maturity, acctStatus2);
                CDAccount newCDaccount = new CDAccount (acct);
                return newCDaccount;
            } else if (acctType2.equals("Checking")) {
                Name name = new Name (firstName, lastName);
                Depositer depositer = new Depositer (name, ssn);
                Calendar maturity = Calendar.getInstance();
                Account acct = new Account(depositer, acctNum3, acctType2, balance3, maturity, acctStatus);
                CheckingAccount newCheckingaccount = new CheckingAccount(acct);
                return newCheckingaccount;
            } else if (acctType2.equals("Savings")) {
                Name name = new Name (firstName, lastName);
                Depositer depositer = new Depositer (name, ssn);
                Calendar maturity = Calendar.getInstance();
                Account acct = new Account(depositer, acctNum3, acctType2, balance3, maturity, acctStatus);
                SavingsAccount newSavingsaccount = new SavingsAccount(acct);
                return newSavingsaccount;
            }
        }
        catch (NumberFormatException e) {
            System.out.print(e);
        }
        return null;
    }
    
    /* Method addAccount()
    *   Input: newAcct - reference to an Account object
    *   Process: Uses the object information to add in the Account arraylist
    *   Output: Adds a new account to the arraylist
    */
    public void addAccount (Account newAcct, TransactionTicket tt) throws FileNotFoundException, IOException {
        Calendar maturityDate = newAcct.getMaturityDate();
        String acctType = newAcct.getAcctType();
        double acctBal = newAcct.getBalance();
        int acctNum = newAcct.getAcctNumber();
        if (acctType.equals("Savings")) {
            addSavingsTotal(acctBal);
        }
        if (acctType.equals("Checking")) {
            addCheckingTotal(acctBal);
        }
        if (acctType.equals("CD")) {
            addCDTotal(acctBal);
        }
        String input = newAcct.toRecord();
        account.seek(account.length());
        account.writeChars(input);
        numberOfAccounts++;
        addAccountTotal(acctBal);
        TransactionReceipt tr = new TransactionReceipt("Open new account success", 0, acctBal, maturityDate, acctNum, tt, acctType);
        addTransaction(tr);
    }
    
    /* Method openNewAccount()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *          acct - Object of the Account class
    *   Process: Calls bank.findAcct() to find arraylist index
    *            If index not found, creates a new account at next open index
    *   Output: Creates a new account if account does not exist
    *           Prints appropriate error message if account number is invalid or in use
    *           Creates and returns a TransactionReceipt object
    */
    public TransactionReceipt openNewAccount(TransactionTicket tt, Account acct) throws IOException {
        int acctNum = tt.getAccountNum();
        int i = findAcct(acctNum);
        try {
        if (i == -1) {
            addAccount(acct, tt);
            double acctBal = acct.getBalance();
            Calendar maturityDate = acct.getMaturityDate();
            String acctType = acct.getAcctType();
            TransactionReceipt tr = new TransactionReceipt("Open new account success", 0, acctBal, maturityDate, acctNum, tt, acctType);
            return tr;
        } else if (i >= 0) {
            //Error: Account number in use
            TransactionReceipt tr = new TransactionReceipt("Open new account error", "Account already in use", acctNum, tt);
            return tr;
        } else {
            //Error: Invalid account number
            throw new InvalidAccountException ("Error: Invalid account number " + acctNum);
        }
    }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr = new TransactionReceipt("Open new account error", "Invalid account number", acctNum, tt);
            return tr;
        }
    }
    
    /* Method getBalance()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *   Process: Calls bank.findAcct() to find arraylist index
    *            Calls bank.getAcct().getBalance() to get balance
    *   Output: Prints out account balance
    *           Prints appropriate error message if account number is not found
    *           Creates and returns a TransactionReceipt object
    */
    public TransactionReceipt getBalance(TransactionTicket tt) throws IOException {
        int reqAcctNum = tt.getAccountNum();
        int i = findAcct(reqAcctNum);
        try {
        if (i >= 0) {
            Account acct = reconstructAccount(i);
            TransactionReceipt tr = acct.getBalance(tt);
            return tr;
        } else {
            //Error: Account not found
            throw new InvalidAccountException (reqAcctNum);
        }
    }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr = new TransactionReceipt("Get balance error", "Account not found", reqAcctNum, tt);
            return tr;
        }
    }
    
    /* Method makeDeposit()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *   Process: Calls bank.findAcct() to find arraylist index
    *            Creates an appropriate Account subclass object and uses polymorphism to call makeDeposit() to change account balance
    *   Output: Prints out process
    *           Prints appropriate error message if account number is not found
    *           Creates and returns a TransactionReceipt object
    */
    public TransactionReceipt makeDeposit(TransactionTicket tt) throws IOException {
        int reqAcctNum = tt.getAccountNum();
        Calendar transactionDate = tt.getDateOfTransaction();
        double reqDeposit = tt.getTransactionAmount();
        int i = findAcct(reqAcctNum);
        try {
        if (i >= 0) {
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            Double oldBal = acct.getBalance();
            int term = tt.getTermofCD();
            if (acctType.equals("CD") && term == 0) {
                TransactionReceipt tr = new TransactionReceipt ("Deposit error", "New term of maturity for CD accounts cannot be zero months.", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
                addTransaction(tr);
                return tr;
            }
            if (acct.getAccountStatus().equals("Closed")) {
                //Error: Cannot perform deposits on closed accounts
                throw new AccountClosedException (acctNum);
            }
            if (acctType.equals("CD") && maturityDate.after(transactionDate)) {
                //Error: CD account maturity date not reached
                throw new CDMaturityDateException(maturityDate);
            } else {
                if (reqDeposit <= 0.00) {
                    //Error: Invalid deposit amount
                    throw new InvalidAmountException (reqDeposit);
                } else {
                    TransactionReceipt tr = acct.makeDeposit(tt);
                    return tr;
                }
            }
        } else {
            //Error: Account not found
            throw new InvalidAccountException (reqAcctNum);
        }
    }
        catch (InvalidAmountException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            Double oldBal = acct.getBalance();
            TransactionReceipt tr = new TransactionReceipt ("Deposit error", "Invalid deposit amount", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr = new TransactionReceipt("Deposit error", "Account not found", reqAcctNum, tt);
            return tr;
        }
        catch (AccountClosedException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            Double oldBal = acct.getBalance();
            TransactionReceipt tr = new TransactionReceipt ("Deposit error", "Closed Account", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
        catch (CDMaturityDateException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            int month = (maturityDate.get(Calendar.MONTH) + 1);
            int day = (maturityDate.get(Calendar.DATE));
            int year = (maturityDate.get(Calendar.YEAR));
            TransactionReceipt tr = new TransactionReceipt ("Deposit error", "Account maturity not reached (" + month + "/" + day + "/" + year + ")", acct.getBalance(), acct.getBalance(), maturityDate, acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
    }
    
    /* Method makeWithdrawal()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *          sc - Object of the Scanner class
    *   Process: Calls bank.findAcct() to find arraylist index
    *            Creates an appropriate Account subclass object to call makeWithdrawal() to change account balance
    *   Output: Prints out process
    *           Prints appropriate error message if account number is not found
    *           Creates and returns a TransactionReceipt object
    */
    public TransactionReceipt makeWithdrawal(TransactionTicket tt) throws IOException {
        int reqAcctNum = tt.getAccountNum();
        Calendar date = tt.getDateOfTransaction();
        double reqWithdrawal = tt.getTransactionAmount();
        int i = findAcct(reqAcctNum);
        try {
        if (i >= 0) {
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            double oldBal = acct.getBalance();
            int term = tt.getTermofCD();
            if (acctType.equals("CD") && term == 0) {
                TransactionReceipt tr = new TransactionReceipt ("Withdrawal error", "New term of maturity for CD accounts cannot be zero months.", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
                addTransaction(tr);
                return tr;
            }
            if (acct.getAccountStatus().equals("Closed")) {
                //Error: Cannot perform withdrawals on closed accounts
                throw new AccountClosedException (acctNum);
            }
            if (reqWithdrawal <= 0) {
                //Error: Invalid withdrawal amount
                throw new InvalidAmountException (reqWithdrawal);
            }
            if (acctType.equals("CD")) {
                if (maturityDate.before(date)) {
                    if (oldBal >= reqWithdrawal) {
                        //TransactionReceipt tr = account.get(i).makeWithdrawal(tt);
                        TransactionReceipt tr = acct.makeWithdrawal(tt);
                        return tr;
                    } else {
                        //Error: Insufficient funds in CD account
                        TransactionReceipt tr = new TransactionReceipt("Withdrawal error", "Insufficient funds", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
                        //account.get(i).addTransaction(tr);
                        addTransaction(tr);
                        return tr;
                    }
                } else {
                    //Error: CD account has not yet reached maturity date
                    throw new CDMaturityDateException(maturityDate);
                }
            } else {
                if (oldBal >= reqWithdrawal) {
                    //TransactionReceipt tr = account.get(i).makeWithdrawal(tt);
                    TransactionReceipt tr = acct.makeWithdrawal(tt);
                    return tr;
                } else {
                    //Error: Insufficient funds in account
                    throw new InsufficientFundsException (reqWithdrawal, oldBal);
                }
            }
        } else {
            throw new InvalidAccountException (reqAcctNum);
            //Error: Account not found
        }
    }
        catch (InvalidAmountException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            Double oldBal = acct.getBalance();
            TransactionReceipt tr = new TransactionReceipt ("Withdrawal; error", "Invalid deposit amount", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr = new TransactionReceipt("Withdrawal error", "Account not found", reqAcctNum, tt);
            return tr;
        }
        catch (AccountClosedException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            Double oldBal = acct.getBalance();
            TransactionReceipt tr = new TransactionReceipt ("Withdrawal error", "Closed Account", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
        catch (InsufficientFundsException e) {
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            Double oldBal = acct.getBalance();
            TransactionReceipt tr = new TransactionReceipt("Withdrawal error", "Insufficient funds", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
        catch (CDMaturityDateException e) {
            Account acct = reconstructAccount(i);
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            int month = (maturityDate.get(Calendar.MONTH) + 1);
            int day = (maturityDate.get(Calendar.DATE));
            int year = (maturityDate.get(Calendar.YEAR));
            int acctNum = acct.getAcctNumber();
            TransactionReceipt tr = new TransactionReceipt("Withdrawal error", "CD account has not yet reached maturity date (" + month + "/" + day + "/" + year + ")", acct.getBalance(), acct.getBalance(), maturityDate, acctNum ,tt, acctType);
            addTransaction(tr);
            return tr;
        }
    }
    
    /* Method clearCheck()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *          chk - Object of the Check class
    *   Process: Calls bank.findAcct() to find arraylist index
    *            Creates a CheckingAccount subclass object to call method clearCheck() to change account balance
    *   Output: Prints out process
    *           Prints appropriate error message if account number is not found
    *           Creates and returns a TransactionReceipt object
    */
    public TransactionReceipt clearCheck(TransactionTicket tt, Check check) throws IOException {
        int reqAcctNum = tt.getAccountNum();
        Check chk = check.getCheckCopy();
        Calendar today = tt.getDateOfTransaction();
        Calendar checkDate = chk.getDateOfCheck();
        int checkMonth = checkDate.get(Calendar.MONTH)+ 1;
        int checkDay = checkDate.get(Calendar.DATE);
        int checkYear = checkDate.get(Calendar.YEAR);
        int i = findAcct(reqAcctNum);
        try {
        if (i >= 0) {
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            double oldBal = acct.getBalance();
            Calendar maturityDate = acct.getMaturityDate();
            if (acct.getAccountStatus().equals("Closed")) {
                throw new AccountClosedException (acctNum);
            }
            if (acctType.equals("Checking")) {
                if (checkDate.after(today)) {
                    //Error: Check is post-dated and cannot be cleared
                    throw new PostDatedCheckException(checkDate);
                } else {
                    Calendar valid = tt.getCheckDate();
                    valid.add(Calendar.MONTH, 6);
                    if (valid.after(today)) {
                        //TransactionReceipt tr = account.get(i).clearCheck(tt);
                        TransactionReceipt tr = acct.clearCheck(tt);
                        return tr;
                    } else {
                        //Error: Check is older than 6 months
                        valid.add(Calendar.MONTH, -6); //Note: Changes to reference object changes source value (checkDate); This ensures correct checkDate is passed to exception
                        throw new CheckTooOldException(checkDate);
                    }
                }
            } else {
                //Error: Cannot clear check in a non-Checking account
                TransactionReceipt tr = new TransactionReceipt("Clear check error", "Cannot clear check on non-Checking account", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
                addTransaction(tr);
                return tr;
            }
        } else {
            //Error: Account not found
            throw new InvalidAccountException (reqAcctNum);
        }
    }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr = new TransactionReceipt("Clear check error", "Account not found", reqAcctNum, tt);
            return tr;
        }
        catch (AccountClosedException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            Double oldBal = acct.getBalance();
            TransactionReceipt tr = new TransactionReceipt ("Clear check error", "Closed Account", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
        catch (PostDatedCheckException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Double oldBal = acct.getBalance();
            TransactionReceipt tr = new TransactionReceipt("Clear check error", "Check is post-dated (" + checkMonth + "/" + checkDay + "/" + checkYear + ") and cannot be cleared", oldBal, acct.getBalance(), acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
        catch (CheckTooOldException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            Double oldBal = acct.getBalance();
            TransactionReceipt tr = new TransactionReceipt("Clear check error", "Check date (" + checkMonth + "/" + checkDay + "/" + checkYear + ") is older than 6 months", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
            addTransaction(tr);
            return tr;
        }
    }
    
    /* Method deleteAcct()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *   Process: Calls bank.findAcct() to find arraylist index
    *            Deletes account from array index if found and account has no existing balance
    *   Output: Prints out process
    *           Prints appropriate error message if account number is not found or account has a balance
    *           Creates and returns a TransactionReceipt object
    */
    public TransactionReceipt deleteAcct(TransactionTicket tt) throws IOException {
        int reqAcctNum = tt.getAccountNum();
        int i = findAcct(reqAcctNum);
        try {
        if (i > 0) {
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            double oldBal = acct.getBalance();
            String acctType = acct.getAcctType();
            Calendar maturityDate = acct.getMaturityDate();
            if (oldBal == 0) {
                //Success: Account deleted
                TransactionReceipt tr = new TransactionReceipt("Delete account success", "Account deleted", oldBal, 0, acctNum, tt, acctType);
                String hold = getAccountInfo(numberOfAccounts - 1);
                int bytesPerChar = 2;
                int charsPerString = 165;
                int interval = bytesPerChar * charsPerString;
                int search = i * interval;
                account.seek(search);
                account.writeChars(hold);
                numberOfAccounts--;
                account.setLength(account.length()-330);
                return tr;
            } else {
                //Error: Cannot delete; account has a non-zero balance
                TransactionReceipt tr = new TransactionReceipt("Delete account error", "Account has an existing balance", oldBal, acct.getBalance(), maturityDate, acctNum, tt, acctType);
                addTransaction(tr);
                return tr;
            }
        } else {
            //Error: Account not found
            throw new InvalidAccountException (reqAcctNum);
        }
    }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr = new TransactionReceipt("Delete account error", "Account not found", reqAcctNum, tt);
            return tr;
        }
    }
    
    /* Method acctInfo()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *          ps - Object of the PrintStream class
    *   Process: Performs a sequential search to find accounts with matching SSN
    *   Output: Prints out process and found accounts
    *           Prints appropriate error message if accounts are not found
    */
    public void acctInfo (TransactionTicket tt, PrintStream ps) throws IOException {
        String reqSSN = tt.getSSN();
        boolean found = false, printOnce = false;
        try {
        for (int j=0; j<numberOfAccounts; j++) {
            account.seek(j*330 + 80);
            String foundSSN = "";
            for (int k=0; k<20; k++) {
                foundSSN += account.readChar();
            }
            String foundSSN2 = foundSSN.trim();
            if (j >= 0 && foundSSN2.equals(reqSSN)) {
                Account acct = reconstructAccount(j);
                String acctType = acct.getAcctType();
                int acctNum = acct.getAcctNumber();
                    found = true;
                    double balance = acct.getBalance();
                    Calendar matDate = acct.getMaturityDate();
                    String acctStatus = acct.getAccountStatus();
                    int month = matDate.get(Calendar.MONTH) + 1;
                    int day = matDate.get(Calendar.DATE);
                    int year = matDate.get(Calendar.YEAR);
                    TransactionReceipt tr1 = new TransactionReceipt("Find account info success", "Account(s) found", reqSSN, tt);
                    if (!printOnce) {
                        ps.printf("\n");
                        ps.printf(tr1.toString());
                        printOnce = true;
                    }
                    ps.printf("\n\n\tAccount #%d\n\tAccount Type: %s\n\tAccount Balance: $%.2f\n\tAccount Status: %s", acctNum, acctType, balance, acctStatus.trim());
                    if (acctType.equals("CD")) {
                        ps.printf("\n\tMaturity Date: %d/%d/%d\n", month, day, year);
                    }
                }
            if (j == numberOfAccounts - 1 && found == false) {
                //No accounts found for the social security number
                throw new InvalidAccountException ("No accounts found for the social security number " + reqSSN +".");
            }
        }
    }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr1 = new TransactionReceipt("Find account info error", "No accounts found for the social security number", reqSSN, tt);
            ps.println();
            ps.printf(tr1.toString());
        }
    }
    
    /* Method acctInfoHistory()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *          ps - Object of the PrintStream class
    *   Process: Performs a sequential search to find accounts with matching SSN
    *   Output: Prints out process and found accounts alongside transaction receipt history for the account number(s)
    *           Prints appropriate error message if accounts are not found
    */
    public void acctInfoHistory (TransactionTicket tt, PrintStream ps) throws Exception {
        String reqSSN = tt.getSSN();
        boolean found = false, printOnce = false;
        try {
        for (int j=0; j<numberOfAccounts; j++) {
            account.seek(j*330 + 80);
            String foundSSN = "";
            for (int k=0; k<20; k++) {
                foundSSN += account.readChar();
            }
            String foundSSN2 = foundSSN.trim();
            if (j >= 0 && foundSSN2.equals(reqSSN)) {
            Account acct = reconstructAccount(j);
            int accountNum = acct.getAcctNumber();
                if (j < numberOfAccounts) {
                    found = true;
                    TransactionReceipt tr1 = new TransactionReceipt("Find account info and history success", "Accounts found", reqSSN, tt);
                    if (!printOnce) {
                        ps.println();
                        ps.printf(tr1.toString());
                        ps.printf("\n\n-----Account and History Information Below-----\n");
                        printOnce = true;
                    }
                    boolean eof = false;
                    while (!eof) {
                    try {
                        for (int k=0; k>=0; k++) {
                            String str = TransactionReceipt.reconstructReceipt(accountNum, k, tt);
                            if (str == null) {
                                break;
                            }
                            ps.println(str);
                            ps.printf("\n-----------------------------");
                        }
                        break;
                    }
                    catch (EOFException ex) {
                        eof = true;
                    }
                }
                if (j == numberOfAccounts-1 && found == false) {
                    //No accounts found for the social security number
                    throw new InvalidAccountException ("No accounts found for the social security number " + reqSSN +".");
                    }
                }
            }
        }
    }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr1 = new TransactionReceipt("Find account info and history error", "No accounts found for the social security number", reqSSN, tt);
            ps.println();
            ps.printf(tr1.toString());
        }
    }
    
    /* Method closeAcct()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *          ps - Object of the PrintStream class
    *   Process: Calls bank.findAcct() to find arraylist index
    *            Checks if account is already closed, and returns an error; Otherwise, closes account
    *   Output: Returns and prints a transaction receipt
    *           Prints appropriate error message if accounts are not found
    */
    public TransactionReceipt closeAcct (TransactionTicket tt) throws IOException {
        int reqAcctNum = tt.getAccountNum();
        int i = findAcct(reqAcctNum);
        try {
        if (i>0) {
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber(); 
            if (acct.getAccountStatus().equals("Open")) {
                TransactionReceipt tr1 = acct.closeAcct(tt);
                return tr1;
            } else {
                //Error: Cannot close an already closed account
                throw new AccountClosedException ("Account: " +acctNum + " is already closed");
            }
        } else {
            //Error: Cannot close account; Account not found
            throw new InvalidAccountException (reqAcctNum);
        }
    }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr1 = new TransactionReceipt("Close account error", "Account not found", reqAcctNum, tt);
            return tr1;
        }
        catch (AccountClosedException e) {
            System.out.print(e.getErrorMessage());
            Account acct = reconstructAccount(i);
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar matDate = acct.getMaturityDate();
            Double acctBal = acct.getBalance();
            TransactionReceipt tr1 = new TransactionReceipt("Close account error", "Account already closed", acctBal, acctBal, matDate, acctNum, tt, acctType);
            addTransaction(tr1);
            return tr1;
        }
    }
    
    /* Method reopenAcct()
    *   Input: bank - Object of the Bank class
    *          tt - Object of the TransactionTicket class
    *          ps - Object of the PrintStream class
    *   Process: Calls bank.findAcct() to find arraylist index
    *            Checks if account is already open, and returns an error; Otherwise, reopens a closed account
    *   Output: Returns and prints a transaction receipt
    *           Prints appropriate error message if accounts are not found
    */
    public TransactionReceipt reopenAcct (TransactionTicket tt) throws IOException {
        int reqAcctNum = tt.getAccountNum();
        int i = findAcct(reqAcctNum);
        try {
        if (i>0) {
            Account acct = reconstructAccount(i);
            double acctBal = acct.getBalance();
            int acctNum = acct.getAcctNumber();
            String acctType = acct.getAcctType();
            Calendar matDate = acct.getMaturityDate(); 
            if (acct.getAccountStatus().equals("Open")) {
                //Error: Cannot reopen an already open account
                TransactionReceipt tr1 = new TransactionReceipt ("Reopen account error", "Account still open", acctBal, acctBal, matDate, acctNum, tt, acctType);
                addTransaction(tr1);
                return tr1;
            } else {
            TransactionReceipt tr1 = acct.reopenAcct(tt);
            return tr1;
            }
        } else {
            //Error: Cannot reopen account; Account not found
            throw new InvalidAccountException (reqAcctNum);
        }
    }
        catch (InvalidAccountException e) {
            System.out.print(e.getErrorMessage());
            TransactionReceipt tr1 = new TransactionReceipt("Reopen account error", "Account not found", reqAcctNum, tt);
            return tr1;
        }
    }
    
    //public Account getAcctCopy (int i) {
    //    Account acct = account.get(i);
    //    Account copy = acct.getAccountCopy();
    //    return copy;
    //}
    
    public int getNumAccts() {
        return numberOfAccounts;
    }
    
    public static void addSavingsTotal(double amount) {
        totalAmountinSavingsAccts += amount;
    }
    
    public static void addCheckingTotal(double amount) {
        totalAmountinCheckingAccts += amount;
    }
    
    public static void addCDTotal(double amount) {
        totalAmountinCDAccts += amount;
    }
    
    public static void addAccountTotal(double amount) {
        totalAmountinAllAccts += amount;
    }
    public static void subtractSavingsTotal(double amount) {
        totalAmountinSavingsAccts -= amount;
    }
    
    public static void subtractCheckingTotal(double amount) {
        totalAmountinCheckingAccts -= amount;
    }
    
    public static void subtractCDTotal(double amount) {
        totalAmountinCDAccts -= amount;
    }
    
    public static void subtractAccountTotal(double amount) {
        totalAmountinAllAccts -= amount;
    }
    
    public static double getSavingsTotal() {
        return totalAmountinSavingsAccts;
    }
    
    public static double getCheckingTotal() {
        return totalAmountinCheckingAccts;
    }
    
    public static double getCDTotal() {
        return totalAmountinCDAccts;
    }
    
    public static double getAcctTotal() {
        return totalAmountinAllAccts;
    }
    
}
