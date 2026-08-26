import java.io.Serializable;

public class User implements Serializable{
    private double balance;
    private String theme;

    public User(double balance, String theme) {
        this.balance = balance;
        this.theme = theme;
    }

    // getters
    public double getBalance() {
        return balance;
    }

    public String getTheme() {
        return theme;
    }

    // setters
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTheme(String Theme) {

    }

}
