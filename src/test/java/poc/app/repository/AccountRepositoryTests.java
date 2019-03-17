package poc.app.repository;

import poc.app.model.Account;
import poc.app.model.AccountType;
import poc.app.model.Transaction;
import poc.app.model.TransactionType;
import poc.app.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
/*
    DAO Integration test with H2 in memory database
 */
public class AccountRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository repo;

    @Test
    public void testFindByAccountNumber() {
        String accountNumber = "1001";
        Account account = new Account(accountNumber, "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 10), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);
        Transaction t3 = new Transaction(account, LocalDate.of(2019, 2, 12), "SGD", new BigDecimal("10"), TransactionType.Debit, null);
        Transaction t4 = new Transaction(account, LocalDate.of(2019, 2, 13), "SGD", new BigDecimal("10"), TransactionType.Credit, null);

        account.addTransaction(t1);
        account.addTransaction(t2);
        account.addTransaction(t3);
        account.addTransaction(t4);

        entityManager.persist(account);

        List<Account> accounts = repo.findByNumber(accountNumber);

        Assert.assertEquals(1, accounts.size());
        Account actual = accounts.get(0);

        Assert.assertEquals(accountNumber, actual.getNumber());
        Assert.assertEquals(4, actual.getTransactions().size());

        // other assertions ...
    }

    @Test
    public void testListAccountsOrderByField() {
        String orderBy = "balanceDate";
        Account account1 = new Account("1001", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");
        Account account2 = new Account("1002", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 20), "SGD");

        entityManager.persist(account1);
        entityManager.persist(account2);

        List<Account> accounts = repo.findAll(Sort.by(Sort.Direction.DESC, orderBy));

        Assert.assertEquals(2, accounts.size());
        Account actual = accounts.get(0);

        Assert.assertEquals("1002", actual.getNumber());

    }

    @Test
    public void testFindByAccountNumberWithPagination() {
        String orderBy = "balanceDate";
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, orderBy));

        Account account1 = new Account("1001", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");
        Account account2 = new Account("1002", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 20), "SGD");

        entityManager.persist(account1);
        entityManager.persist(account2);

        Page<Account> actual = repo.findAll(pageable);

        Assert.assertEquals(2, actual.getTotalPages());
        Assert.assertEquals(2, actual.getTotalElements());

    }

}