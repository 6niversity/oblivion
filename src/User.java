import java.io.Serializable;

public class User implements Serializable{
    private double balance;

    public User(double balance) {
        this.balance = balance;
    }

    // getters
    public double getBalance() {
        return balance;
    }

    // setters
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
