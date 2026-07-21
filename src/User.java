public class User {
    private double balance;
    private String username;

    public User(int balance, String username) {
        this.balance = balance;
        this.username = username;
    }

    // getters
    private double getBalance() {
        return balance;
    }

    private String getUsername() {
        return username;
    }

    // setters
    private void setBalance(double balance) {
        this.balance = balance;
    }

    private void setUsername(String username) {
        this.username = username;
    }
}
