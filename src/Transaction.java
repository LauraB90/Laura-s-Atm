import java.util.ArrayList;
import java.util.Date;

public class Transaction {

    private double amount;
    private Date timeStamp;
    private String memo;
    private Account inAccount;
    //^^the account in which the transaction was made.


    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timeStamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount) {
        //call the arg contrucotor first
        this(amount, inAccount);
        //set the memo
        this.memo = memo;


    }
}
