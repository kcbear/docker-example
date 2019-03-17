package poc.app.repository;

import poc.app.model.Account;
import poc.app.model.AccountType;
import poc.app.model.Transaction;
import poc.app.model.TransactionType;
import poc.app.repository.TransactionRepository;
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
public class TransactionRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository repo;

    @Test
    public void testFindByAccountNumber() {
        String accountNumber = "1001";
        String accountName = "SGSaving726";
        Account account = new Account(accountNumber, accountName, AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 10), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);

        account.addTransaction(t1);
        account.addTransaction(t2);

        entityManager.persist(account);

        List<Transaction> actual = repo.findByAccountNumber(accountNumber);

        Assert.assertEquals(2, actual.size());

        for (Transaction txn : actual) {
            Assert.assertEquals(accountNumber, txn.getAccount().getNumber());
            Assert.assertEquals(accountName, txn.getAccount().getName());
        }

        // other assertions ...
    }

    @Test
    public void testFindByAccountNumberWithSorting() {
        String orderBy = "valueDate";

        String accountNumber = "1001";
        String accountName = "SGSaving726";
        Account account = new Account(accountNumber, accountName, AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 10), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);

        account.addTransaction(t1);
        account.addTransaction(t2);

        entityManager.persist(account);

        List<Transaction> actual = repo.findByAccountNumber(accountNumber, Sort.by(Sort.Direction.DESC, orderBy));

        Assert.assertEquals(2, actual.size());

        for (Transaction txn : actual) {
            Assert.assertEquals(accountNumber, txn.getAccount().getNumber());
            Assert.assertEquals(accountName, txn.getAccount().getName());
        }

        Assert.assertEquals(true, actual.get(0).getValueDate().isAfter(actual.get(1).getValueDate()));
    }

    @Test
    public void testFindByAccountNumberWithPagination() {
        String orderBy = "valueDate";
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, orderBy));

        String accountNumber = "1001";
        String accountName = "SGSaving726";
        Account account = new Account(accountNumber, accountName, AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 10), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);

        account.addTransaction(t1);
        account.addTransaction(t2);

        entityManager.persist(account);

        Page<Transaction> actual = repo.findByAccountNumber(accountNumber, pageable);

        Assert.assertEquals(2, actual.getTotalPages());
        Assert.assertEquals(2, actual.getTotalElements());

        for (Transaction txn : actual) {
            Assert.assertEquals(accountNumber, txn.getAccount().getNumber());
            Assert.assertEquals(accountName, txn.getAccount().getName());
        }

    }

}