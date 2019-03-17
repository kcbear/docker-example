package poc.app.service;

import poc.app.model.Account;
import poc.app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    protected AccountService() {
    }

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> listAccounts() {

        return accountRepository.findAll();
    }

    public List<Account> listAccountsOrderByField(Sort.Direction direction, String orderBy) {
        return accountRepository.findAll(Sort.by(direction, orderBy));
    }

    public Page<Account> listAccountsWithPage(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Page<Account> listFirstPageOfAccountsOrderByBalanceDateDesc(int noOfResult) {
        String orderBy = "balanceDate";
        Pageable pageable = PageRequest.of(0, noOfResult, Sort.by(Sort.Direction.DESC, orderBy));
        return accountRepository.findAll(pageable);
    }

}
