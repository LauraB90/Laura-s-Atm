package src;

import java.sql.SQLOutput;
import java.util.Scanner;

public class ATM {

    public static void main (String[] args){

        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Bangtan's Bank");

                    //add a user (includes savings acc)
        User aUser = theBank.addUser("Namjoon", "Kim", "1234");
                    // add a checking acc
        Account newAccount = new Account("Checking", aUser, theBank);
            aUser.addAccount(newAccount);
            theBank.addAccount(newAccount);


            User curUser;
            while (true){

                //stay in the login prompt until successful login
                curUser = ATM.mainMenuPrompt(theBank, sc);

                //stay in main menu until user quits
                ATM.mainMenu(curUser, sc);
            }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc ){
        String userId;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userId = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

 // try to get a user object correnponding to the Id and pin combo.
    authUser = theBank.userLogin(userId, pin);
    if( authUser == null){
        System.out.println("Incorrect user Id/Pin combination. " + "Please try again");
    }

        }while(authUser == null);

        return authUser;
    }

    public static void mainMenu(User elUser, Scanner sc){

        elUser.printAccountSummary();

        int choice;

        do{
            System.out.printf("Welcome %s, what would you like to do?\n", elUser.getFirstName());

            System.out.println("  1) Show account transaction");
            System.out.println("  2) Withdrawal");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.println("Enter choice :");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice, please try again 1-5");
            }
        } while (choice < 1 || choice > 5);

        switch (choice){

            case 1:
                ATM.transHistory(elUser, sc);
                break;
            case 2:
                ATM.withdrawals(elUser, sc);
                break;
            case 3:
                ATM.deposits(elUser, sc);
                break;
            case 4:
                ATM.transfers(elUser, sc);
                break;
            case 5:
                // gobble up the rest of previos input
                sc.nextLine();
                break;
        }

        if (choice != 5){
            ATM.mainMenu(elUser, sc);
        }
    }

// show the transaction history for an acc

    public static void transHistory(User theUser, Scanner sc){
        int theAcct;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you want to see: ",
                    theUser.numAccounts());
                theAcct = sc.nextInt()-1;
                if (theAcct < 0 || theAcct >= theUser.numAccounts()){
                    System.out.println("Invalid Account. Please try again.");
                }
        } while(theAcct < 0 || theAcct >= theUser.numAccounts());

        theUser.printAccTransHistory(theAcct);

    }
    public static void transfers(User theUser, Scanner sc){

        int fromAcct;
        int toAcct;
        double amount;
        double accBal;

    // get acc transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from : ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("invalid Account. Please try again.");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
         accBal = theUser.getAccBalance(fromAcct);

        //get the acc to transfer
        do {
            System.out.printf("Enter the number (1 - %d) of the account\n" +
                    "to transfer to : ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("invalid Account. Please try again.");
            }
        }while (toAcct < 0 || toAcct >= theUser.numAccounts());


        // get the amount to transfer
      do {
          System.out.printf("Enter the amount to transfer (max $%.0f): $",
                                     accBal);
          amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero");
            } else if (amount > accBal){
                System.out.printf("Amount must be greater than\n" +
                        "balance of $%.02f.\n", accBal);
            }
      } while(amount < 0 || amount > accBal);

  // do the transfer
       theUser.addAccTransaction(fromAcct, -1*amount, String.format(
               "Transfer to account %s", theUser.getAccUUID(toAcct)));
        theUser.addAccTransaction(toAcct, amount, String.format(
                "Transfer to account %s", theUser.getAccUUID(toAcct)));
    }

    public static void withdrawals(User theUser, Scanner sc){

        int fromAcct;
        double amount;
        double accBal;
        String memo;

        // get acc transfer from
        do {
            System.out.printf("Enter the number (1 - %d) of the account\n" +
                    "to withdraw from : ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("invalid Account. Please try again.");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        accBal = theUser.getAccBalance(fromAcct);


        do {
            System.out.printf("Enter the amount to withdraw (max $%.0f): $",
                    accBal);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero");
            } else if (amount > accBal){
                System.out.printf("Amount must be greater than\n" +
                        "balance of $%.02f.\n", accBal);
            }
        } while(amount < 0 || amount > accBal);

        // gobble up the rest of previos input
        sc.nextLine();

        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // the withdrawl
        theUser.addAccTransaction(fromAcct, -1*amount, memo);
    }
    public static void deposits(User theUser, Scanner sc){

        int toAcc;
        double amount;
        double accBal;
        String memo;

        // get acc transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", theUser.numAccounts());
            toAcc = sc.nextInt()-1;
            if (toAcc < 0 || toAcc >= theUser.numAccounts()){
                System.out.println("invalid Account. Please try again.");
            }
        }while (toAcc < 0 || toAcc >= theUser.numAccounts());
        accBal = theUser.getAccBalance(toAcc);


        do {
            //System.out.println("Enter the amount to deposit");

            System.out.printf("Enter the amount to deposit (max $%.0f): $",
                    accBal);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero");

            }
        } while(amount < 0);

        // gobble up the rest of previos input
        sc.nextLine();

        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // the withdrawl
        theUser.addAccTransaction(toAcc, amount, memo);

    }

}
