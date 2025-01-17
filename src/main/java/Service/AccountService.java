package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO a) {
        this.accountDAO = a;
    }

    public Account createAccount(Account a) {
        if (a.getPassword().length() < 4 || a.getUsername().isBlank()) {
            return null;
        }
        return this.accountDAO.insertAccount(a);
    }

    public Account login(Account a) {
        return this.accountDAO.login(a);
    }
    


    
}
