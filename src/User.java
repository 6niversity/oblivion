public class User {
    private int balance;
    private String username;

    public User(int balance, String username) {
        this.balance = balance;
        this.username = username;
    }

    // getters
    private int getBalance() {
        return balance;
    }

    private String getUsername() {
        return username;
    }

    // setters
    private void setBalance(int balance) {
        this.balance = balance;
    }

    private void setUsername(String username) {
        this.username = username;
    }
}
