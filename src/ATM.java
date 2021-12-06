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
        System.out.println("Incorrect usser Id/Pin combination. " + "Please try again");
    }

        }while(authUser == null);

        return authUser;
    }
}
