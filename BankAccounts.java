
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class BankAccounts {

    public static void main (String[] args) throws Exception{
        boolean show = true;
        Scanner sc = new Scanner (new File ("Testcases.txt"));
        //Scanner sc = new Scanner (System.in);
        PrintStream ps = new PrintStream ("Output.txt");
        Bank bank = new Bank();
        readAccts(bank);
        ps.println ("--------------------\nInitial database:\n--------------------\n");
        printAccts(bank, ps);
        ps.printf("\n\n");
        ps.printf("--------------------Transaction History--------------------");
        do {
            menu();
            char input = sc.next().charAt(0);
            switch (input) {
            case 'W':
            case 'w':
                withdrawal (bank, sc, ps);
                break;
            case 'D':
            case 'd':
                deposit (bank, sc, ps);
                break;
            case 'C':
            case 'c':
                clearCheck (bank, sc, ps);
                break;
            case 'N':
            case 'n':
                newAcct (bank, sc, ps);
                break;
            case 'B':
            case 'b':
                balance (bank, sc, ps);
                break;
            case 'I':
            case 'i':
                accountInfo (bank, sc, ps);
                break;
            case 'H':
            case 'h':
                accountInfoHistory (bank, sc, ps);
                break;
            case 'S':
            case 's':
                closeAcct (bank, sc, ps);
                break;
            case 'R':
            case 'r':
                reopenAcct (bank, sc, ps);
                break;
            case 'X':
            case 'x':
                deleteAcct (bank, sc, ps);
                break;
            case 'Q':
            case 'q':
                show = false;
                System.out.println("Quitting program.");
                break;
            default:
                try {
                ps.printf ("\nInvalid selection: %s\n", input);
                System.out.println();
                throw new InvalidMenuSelectionException(input);
                }
                catch (InvalidMenuSelectionException e) {
                    System.out.print(e.getErrorMessage());
                }
            }
	} while (show);
        ps.println("\n-------------------------------------------------------------------");
        ps.printf("\n\n");
        ps.println("--------------------\nFinal database:\n--------------------\n");
        printAccts(bank, ps);
    }
    
    /* Method menu()
    *   Input: None
    *   Process: Sets up the user menu
    *   Output: Prints out the user menu 
    */
    public static void menu() {
        System.out.println("\n------------------------------");
	System.out.println ("Select one of the following:");
	System.out.println ("\tW - Withdrawal\n\tD - Deposit\n\tC - Clear Check\n\tN - New Account\n\tB - Balance\n\tI - Account Info\n\tX = Delete Account\n\tQ - Quit");
        System.out.println("------------------------------\n");
    }
    
    /* Method readAccts()
    *   Input: bank - Object of the Bank class
    *   Process: Reads account information and creates a Name, Depositer, Calendar, and Account object
    *            Calls method addAccount() from the Bank class to store account information
    *            Sets maturity date from file in MM/DD/YYYY format; Assumes account status is 'open' at creation
    *   Output: Calls bank.addAccount() to store customer information in an array
    */
    public static void readAccts(Bank bank) throws Exception{
        Scanner sc = new Scanner (new File ("InitialDatabase.txt"));
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] token = line.split(" ");
            Name name = new Name(token[0], token[1]);
            Depositer depositer = new Depositer (name, token[2]);
            if (token[4].equals("CD")) {
                int month = Integer.parseInt(token[6]) + 1;
                int day = Integer.parseInt(token[7]);
                int year = Integer.parseInt(token[8]);
                String acctStatus = token[9];
                Calendar cdate = Calendar.getInstance();
                cdate.set(Calendar.MONTH, month);
                cdate.set(Calendar.DAY_OF_MONTH, day);
                cdate.set(Calendar.YEAR, year);
                TransactionTicket tt = new TransactionTicket (Integer.parseInt(token[3]), cdate, "New Account", Double.parseDouble(token[5]), token[2]);
                Account acct = new Account (depositer, Integer.parseInt(token[3]), (token[4]), Double.parseDouble(token[5]), cdate, acctStatus);
                Account newCDAcct = new CDAccount (acct);
                bank.addAccount(newCDAcct, tt);
            } else {
            Calendar date = Calendar.getInstance();
            String acctStatus = token[6];
            TransactionTicket tt = new TransactionTicket (Integer.parseInt(token[3]), date, "New Account", Double.parseDouble(token[5]), token[2]);
            Account acct = new Account (depositer, Integer.parseInt(token[3]), (token[4]), Double.parseDouble(token[5]), date, acctStatus);
            switch (token[4]) {
                case "Checking":
                    Account newCheckingAcct = new CheckingAccount (acct);
                    bank.addAccount(newCheckingAcct, tt);
                    break;
                case "Savings":
                    Account newSavingsAcct = new SavingsAccount (acct);
                    bank.addAccount(newSavingsAcct, tt);
                    break;
                }
            }
        }
    }
    
    /* Method withdrawal()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for an account number and withdrawal amount to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.makeWithdrawal
    *   Output: bank.MakeWithdrawal() utilizes TransactionTicket information to determine if the request is valid
    *           TransactionReceipt method PrintReceipt() to print transaction information to file
    */
    public static void withdrawal(Bank bank, Scanner sc, PrintStream ps) throws IOException {
        System.out.println("Transaction requested: Withdrawal");
        System.out.println("Enter your account number");
        int acctNum = sc.nextInt();
        System.out.println("Enter the amount you want to withdraw");
        Double reqWithdraw = sc.nextDouble();
        System.out.println("If this transaction is on a CD account, enter the number of months of maturity for your CD account from the following selections");
        System.out.println("6 12 18 24");
        System.out.println("If this transaction is NOT on a CD account, enter '0'");
        int term = sc.nextInt();
        boolean check = false;
        do {
            if (term == 6 || term == 12 || term == 18 || term == 24) {
                check = true;
            } else if (term == 0) {
                check = true;
            } else {
                check = false;
                System.out.println("Enter a valid number for term length.");
                term = sc.nextInt();
            }
        } while (check == false);
        Calendar date = Calendar.getInstance();
        TransactionTicket tt = new TransactionTicket(acctNum, date, "Withdrawal", reqWithdraw, term);
        TransactionReceipt tr1 = bank.makeWithdrawal(tt);
        ps.println();
        ps.printf(tr1.toString());
    }
    
    /* Method deposit()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for an account number and deposit amount to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.makeDeposit
    *   Output: bank.MakeDeposit() utilizes TransactionTicket information to determine if the request is valid
    *           TransactionReceipt method PrintReceipt() to print transaction information to file
    */
    public static void deposit(Bank bank, Scanner sc, PrintStream ps) throws IOException {
        System.out.println("Transaction requested: Deposit");
        System.out.println("Enter your account number");
        int acctNum = sc.nextInt();
        System.out.println("Enter the amount you want to deposit");
        Double reqDeposit = sc.nextDouble();
        System.out.println("If this transaction is on a CD account, enter the number of months of maturity for your CD account from the following selections");
        System.out.println("6 12 18 24");
        System.out.println("If this transaction is NOT on a CD account, enter '0'");
        int term = sc.nextInt();
        boolean check = false;
        do {
            if (term == 6 || term == 12 || term == 18 || term == 24) {
                check = true;
            } else if (term == 0) {
                check = true;
            } else {
                check = false;
                System.out.println("Enter a valid number for term length.");
                term = sc.nextInt();
            }
        } while (check == false);
        Calendar date = Calendar.getInstance();
        TransactionTicket tt = new TransactionTicket(acctNum, date, "Deposit", reqDeposit, term);
        TransactionReceipt tr1 = bank.makeDeposit(tt);
        ps.println();
        ps.printf(tr1.toString());
    }
    
    /* Method newAcct()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for complete account information to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.openNewAccount()
    *            Newly created accounts are set with 'open' account status
    *   Output: bank.openNewAccount() utilizes TransactionTicket information to determine if the request is valid
    *           TransactionReceipt method PrintReceipt() to print transaction information to file
    */
    public static void newAcct(Bank bank, Scanner sc, PrintStream ps) throws IOException {
        Calendar today = Calendar.getInstance();
        System.out.println("Transaction requested: Create new account");
        System.out.println("Enter a new 6 digit account number");
        int reqAccount = sc.nextInt();
        System.out.println("Enter your first name.");
        String first = sc.next();
        System.out.println("Enter your last name.");
        String last = sc.next();
        Name name = new Name(first, last);
        System.out.println("Enter your social security number.");
        String ssn = sc.next();
        Depositer depositer = new Depositer (name, ssn);
        System.out.println("[Case Sensitive] Enter the corresponging account type you want to create from the following selections.");
        System.out.println("Checking Savings CD");
        Boolean check = false;
        String acctType = sc.next();
        do {
            if (acctType.equals("Checking") || acctType.equals("Savings") || acctType.equals("CD")) {
                check = true;
            } else {
                System.out.println("Please enter a valid account type");
                acctType = sc.next();
            }
        } while (check == false);
        System.out.println("Enter the initial deposit amount.");
        double initialDeposit = sc.nextDouble();
        while (initialDeposit < 0.00) {
            System.out.println("Enter a valid initial deposit amount.");
            initialDeposit = sc.nextDouble();
        }
        if (acctType.equals("CD")) {
            System.out.println("Enter the number of months of maturity for your CD account from the following selections");
            System.out.println("6 12 18 24");
            int term = sc.nextInt();
            do {
                if (term == 6 || term == 12 || term == 18 || term == 24) {
                    check = true;
                } else {
                    check = false;
                    System.out.println("Enter a valid number for term length.");
                    term = sc.nextInt();
                }
            } while (check == false);
            Calendar maturityDate = Calendar.getInstance();
            maturityDate.add(Calendar.MONTH, term);
            Account acct1 = new Account (depositer, reqAccount, acctType, initialDeposit, maturityDate, "Open");
            CDAccount acct2 = new CDAccount (acct1);
            TransactionTicket tt = new TransactionTicket(reqAccount, today, "New Account", initialDeposit, term);
            TransactionReceipt tr1 = bank.openNewAccount(tt, acct2);
            ps.println();
            ps.printf(tr1.toString());
        } else {
            Calendar maturityDate = Calendar.getInstance();
            if (acctType.equals("Checking")) {
                Account acct3 = new Account (depositer, reqAccount, acctType, initialDeposit, maturityDate, "Open");
                CheckingAccount acct4 = new CheckingAccount(acct3);
                TransactionTicket tt = new TransactionTicket(reqAccount, today, "New Account", initialDeposit, 0);
                TransactionReceipt tr1 = bank.openNewAccount(tt, acct4);
                ps.println();
                ps.printf(tr1.toString());
            }
            if (acctType.equals("Savings")) {
                Account acct5 = new Account (depositer, reqAccount, acctType, initialDeposit, maturityDate, "Open");
                SavingsAccount acct6 = new SavingsAccount(acct5);
                TransactionTicket tt = new TransactionTicket(reqAccount, today, "New Account", initialDeposit, 0);
                TransactionReceipt tr1 = bank.openNewAccount(tt, acct6);
                ps.println();
                ps.printf(tr1.toString());
            }
        }
    }
    
    /* Method deleteAcct()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for an account number to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.deleteAcct()
    *   Output: bank.deleteAcct() utilizes TransactionTicket information to determine if the request is valid
    *           TransactionReceipt method PrintReceipt() to print transaction information to file
    */
    public static void deleteAcct(Bank bank, Scanner sc, PrintStream ps) throws IOException {
        System.out.println("Transaction requested: Delete account");
        System.out.println("Enter the account number");
        String type = "Delete account";
        int reqAccount = sc.nextInt();
        Calendar date = Calendar.getInstance();
        TransactionTicket tt = new TransactionTicket(reqAccount, date, type);
        TransactionReceipt tr1 = bank.deleteAcct(tt); 
        ps.println();
        ps.printf(tr1.toString());
    }
    
    /* Method balance()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for an account number to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.getBalance()
    *   Output: bank.getBalance() utilizes TransactionTicket information to determine if the request is valid
    *           TransactionReceipt method PrintReceipt() to print transaction information to file
    */
    public static void balance(Bank bank, Scanner sc, PrintStream ps) throws IOException {
        System.out.println("Transaction selected: Get balance");
        System.out.println("Enter your account number");
        int acctNum = sc.nextInt();
        String type = "Check balance";
        Calendar date = Calendar.getInstance();
        TransactionTicket tt = new TransactionTicket (acctNum, date, type);
        TransactionReceipt tr1 = bank.getBalance(tt);
        ps.println();
        ps.printf(tr1.toString());
    }
    
    /* Method accountInfo()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for a social security number to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.acctInfo
    *   Output: bank.acctInfo() utilizes TransactionTicket information to find and print account information if found
    */
    public static void accountInfo(Bank bank, Scanner sc, PrintStream ps) throws IOException {
        System.out.println("Requested Transaction: Account Info");
        System.out.println("Enter your social security number");
        String ssn = sc.next();
        Calendar date = Calendar.getInstance();
        String type = "Account info";
        TransactionTicket tt = new TransactionTicket(ssn, date, type);
        bank.acctInfo(tt, ps);
    }
    
    /* Method accountInfoHistory()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for a social security number to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.acctInfoHistory()
    *   Output: bank.acctInfoHistory() utilizes TransactionTicket information to find and print account information with transaction history if found
    */
    public static void accountInfoHistory(Bank bank, Scanner sc, PrintStream ps) throws IOException, Exception {
        System.out.println("Requested Transaction: Account Info and history");
        System.out.println("Enter your social security number");
        String ssn = sc.next();
        Calendar date = Calendar.getInstance();
        String type = "Account info and history";
        TransactionTicket tt = new TransactionTicket(ssn, date, type);
        bank.acctInfoHistory(tt, ps);
    }
    
    /* Method closeAcct()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for an account number to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.closeAcct()
    *   Output: bank.closeAcct() utilizes TransactionTicket information to determine if the request is valid
    *           bank.closeAcct() will then process any changes
    */
    public static void closeAcct (Bank bank, Scanner sc, PrintStream ps) throws IOException {
        System.out.println("Requested Transaction: Close account");
        System.out.println("Enter your account number");
        int acctNum = sc.nextInt();
        Calendar date = Calendar.getInstance();
        String type = "Close account";
        TransactionTicket tt = new TransactionTicket(acctNum, date, type);
        TransactionReceipt tr = bank.closeAcct(tt);
        ps.println();
        ps.printf(tr.toString());
    }
    
    /* Method reopenAcct()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for an account number to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.reopenAcct()
    *   Output: bank.reopenAcct() utilizes TransactionTicket information to determine if the request is valid
    *           bank.reopenAcct() will then process any changes
    */
    public static void reopenAcct (Bank bank, Scanner sc, PrintStream ps) throws IOException {
        System.out.println("Requested Transaction: Reopen account");
        System.out.println("Enter your account number");
        int acctNum = sc.nextInt();
        Calendar date = Calendar.getInstance();
        String type = "Reopen account";
        TransactionTicket tt = new TransactionTicket(acctNum, date, type);
        TransactionReceipt tr = bank.reopenAcct(tt);
        ps.println();
        ps.printf(tr.toString());
    }

    /* Method clearCheck()
    *   Input: bank - Object of the Bank class
    *          sc - Object of the Scanner class
    *          ps - Object of the PrintStream class
    *   Process: Prompts user for an account number and check information to create a TransactionTicket object
    *            Uses the TransactionTicket object to call method bank.clearCheck()
    *   Output: bank.clearCheck() utilizes TransactionTicket information to determine if the request is valid
    *           TransactionReceipt method PrintReceipt() to print transaction information to file
    */
    public static void clearCheck (Bank bank, Scanner sc, PrintStream ps) throws IOException {
        System.out.println("Transaction selected: Clear Check");
        System.out.println("Enter your account number.");
        Calendar today = Calendar.getInstance();
        int reqAccount = sc.nextInt();
        System.out.println("Enter the check amount");
        double checkAmount = sc.nextDouble();
        System.out.println("Enter the month of the check");
        int month = sc.nextInt();
        month = month - 1;
        System.out.println("Enter the day of the check");
        int day = sc.nextInt();
        System.out.println("Enter the year of the check");
        int year = sc.nextInt();
        Calendar checkDate = Calendar.getInstance();
        checkDate.set(year, month, day);
        Check chk = new Check(reqAccount, checkAmount, checkDate);
        TransactionTicket tt = new TransactionTicket(reqAccount, today, "Clear Check", checkAmount, checkDate);
        TransactionReceipt tr1 = bank.clearCheck(tt, chk);
        ps.println();
        ps.printf(tr1.toString());
    }
    
    /* Method printAccts()
    *   Input: bank - Object of the Bank class
    *          ps - Object of the PrintStream class
    *   Process: Utilizes a 'for' loop to get account information
    *            Formats maturity date information to MM/DD/YYYY format
    *   Output: Prints out the information in a chart to display the a chart
    */
    public static void printAccts(Bank bank, PrintStream ps) throws Exception{
        DecimalFormat d = new DecimalFormat("$#,##0.00");
        ps.printf("\n%-17s %-17s %-17s %-17s %-17s %-20s %-18s %-18s\n", "Last Name", "First Name", "SSN", "Account Num.", "Account Type", "Account Status", "Balance", "Maturity Date");
        for (int i=0; i<bank.getNumAccts(); i++) {
            Account acct = bank.reconstructAccount(i);
            String str = acct.toString();
            ps.printf(str);
        }
        String checkingT = d.format(bank.getCheckingTotal());
        String savingsT = d.format(bank.getSavingsTotal());
        String cdT = d.format(bank.getCDTotal());
        String total = d.format(bank.getAcctTotal());
        ps.printf("\n\nTotal Amount in Checking Accounts: %s", checkingT);
        ps.printf("\nTotal Amount in Savings Accounts: %s", savingsT);
        ps.printf("\nTotal Amount in CD Accounts: %s", cdT);
        ps.printf("\nTotal Amount in All Accounts: %s", total);
    }
}
