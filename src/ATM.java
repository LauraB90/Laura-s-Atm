import java.util.Scanner;

public class ATM {

    public static void main (String[] args){

        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Yoongi's Bank");

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
                ATM.printUserMenu(curUser, sc);
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

    public static void printUserMenu(User theUser, Scanner sc){

        theUser.printAccountSummary();

        int choice;

        do{
            System.out.printf("Welcome %s, what would you like to do ?", theUser.getFirstName());

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
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawalFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
        }

        if (choice != 5){
            ATM.printUserMenu(theUser, sc);
        }
    }

// show the transaction history for an acc

    public static void showTransHistory(User theUser, Scanner sc){
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
}
