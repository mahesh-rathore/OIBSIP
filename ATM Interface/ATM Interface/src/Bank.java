import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();

        accounts.add(new Account("1001", "mahesh", "1234", 10000));
        accounts.add(new Account("1002", "rahul", "5678", 5000));
    }

    public Account findAccount(String userId, String pin) {

        for (Account account : accounts) {
            if (account.getUserId().equals(userId)
                    && account.getPin().equals(pin)) {
                return account;
            }
        }

        return null;
    }

    public Account findAccountById(String accountId) {

        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }

        return null;
    }
}
