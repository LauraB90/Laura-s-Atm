package src;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String fistName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;


    //users account pin #
    //the bank object that the user in a customer of.
    public User(String fistName, String lastName, String pin, Bank theBank) {
        this.fistName = fistName;
        this.lastName = lastName;
        //store the pins md5 hash, rather than original value for security purposes.
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error, caught NoSuchAlgorithm");
            e.printStackTrace();
            System.exit(1);
        }
//
        //
        //get a new unique id
        this.uuid = theBank.getNewUserUUID();
        // create an empty list of acc
        this.accounts = new ArrayList<Account>();

        System.out.printf("New User %s, %s with ID %s created.\n", lastName, fistName, this.uuid);
    }

    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public String getUUID(){
        return this.uuid;
    }

    public boolean validatePin(String aPin){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash );
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error, caught NoSuchAlgorithm");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName(){
        return this.fistName;
    }

    //print the accs summary 1:10
    public void printAccountSummary(){
        System.out.printf("\n\n%s's accounts summary\n", this.fistName);
        for(int a = 0; a < accounts.size(); a++){
            System.out.printf("%d) %s\n", a+1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts(){
        return this.accounts.size();
    }

    public void printAccTransHistory(int accIdx){
        this.accounts.get(accIdx).printTransHistory();
    }

    public double getAccBalance(int accIdx){
        return this.accounts.get(accIdx).getBalance();
    }

    public String getAccUUID(int accIdx){
        return this.accounts.get(accIdx).getUUID();
    }

    public void addAccTransaction(int accIdx, double amount, String memo){
        this.accounts.get(accIdx).addTransaction(amount, memo);

    }
}

