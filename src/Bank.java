import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank (String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID() {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique = false;
        //continue looping untill we get a unique one|.
        do {
            uuid = "";
            for (int c = 0; c < len; c++) {
                //adding a digit between 0-9 in a string form
                //and pending it to the end of the uuid String
                uuid += ((Integer) rng.nextInt(10)).toString();
            }
            //iterates thru all the user objects to check if it's unique.
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) { // if the strings are the same than nonUnique is true;
                    nonUnique = true;
                    break;
                }

            }

        } while (nonUnique);

        return uuid;
    }

    public void addAccount (Account anAccount){
        this.accounts.add(anAccount);
    }

    public String getNewAccountUUID() {

        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
        //continue looping untill we get a unique one|.
        do {
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }
            //iterates thru all the user objects to cjeck if its unique.
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }

            }

        } while (nonUnique);

        return uuid;
    }



    public User addUser(String firstName, String lastName, String pin){

        //create a new user object and add to list.
        //this = the object of the bank
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //create savings account for the user and add user and bank acc's lists
        Account newAccount = new Account("savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }


    public User userLogin(String userId, String pin){

        // Loop throuh the list of users;
        for(User u : this.users){
        // if we find the users, check if the ID is correct
            if (u.getUUID().compareTo(userId) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        // if didnt find a user or the pin was incorrect
        return null;
    }

    public String getName() {
        return name;
    }
}
