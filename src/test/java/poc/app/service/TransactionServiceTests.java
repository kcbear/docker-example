package poc.app.service;

import poc.app.model.Account;
import poc.app.model.AccountType;
import poc.app.model.Transaction;
import poc.app.model.TransactionType;
import poc.app.repository.TransactionRepository;
import poc.app.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TransactionServiceTests {

    private TransactionRepository repo = mock(TransactionRepository.class);

    private TransactionService service = new TransactionService(repo);

    @Test
    public void testListTransactionsByAccountNumberReturnNothing() {

        String accountNumber = "1001";
        when(repo.findByAccountNumber(anyString())).thenReturn(new ArrayList<>());

        List<Transaction> actual = service.listTransactionsByAccountNumber(accountNumber);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(repo).findByAccountNumber(captor.capture());

        Assert.assertEquals(accountNumber, captor.getValue());
        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void testListTransactionsByAccountNumber() {

        String accountNumber = "1001";

        Account account = new Account(accountNumber, "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 10), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);

        List<Transaction> expected = new ArrayList<>();
        expected.add(t1);
        expected.add(t2);

        when(repo.findByAccountNumber(anyString())).thenReturn(expected);

        List<Transaction> actual = service.listTransactionsByAccountNumber(accountNumber);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(repo).findByAccountNumber(captor.capture());

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testListTransactionsByAccountNumberOrderByField() {
        String accountNumber = "1001";
        String orderBy = "valueDate";

        Account account = new Account(accountNumber, "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 10), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);

        List<Transaction> expected = new ArrayList<>();
        expected.add(t1);
        expected.add(t2);

        when(repo.findByAccountNumber(anyString(), any(Sort.class))).thenReturn(expected);

        List<Transaction> actual = service.listTransactionsByAccountNumberOrderByField(accountNumber, Sort.Direction.DESC, orderBy);

        ArgumentCaptor<String> captorAccount = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Sort> captorSort = ArgumentCaptor.forClass(Sort.class);

        verify(repo).findByAccountNumber(captorAccount.capture(), captorSort.capture());

        Assert.assertEquals(accountNumber, captorAccount.getValue());
        Assert.assertEquals(Sort.by(Sort.Direction.DESC, orderBy), captorSort.getValue());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testListTransactionsWithPageByAccountNumber() {
        String accountNumber = "1001";
        String orderBy = "valueDate";

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, orderBy));

        Account account = new Account(accountNumber, "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 12), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);

        List<Transaction> expected = new ArrayList<>();
        expected.add(t1);
        expected.add(t2);

        Page<Transaction> foundPage = new PageImpl<>(expected);

        when(repo.findByAccountNumber(anyString(), any(Pageable.class))).thenReturn(foundPage);

        Page<Transaction> actual = service.listTransactionsWithPageByAccountNumber(accountNumber, pageable);

        ArgumentCaptor<String> captorAccount = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> captorPageable = ArgumentCaptor.forClass(Pageable.class);

        verify(repo).findByAccountNumber(captorAccount.capture(), captorPageable.capture());

        Assert.assertEquals(accountNumber, captorAccount.getValue());
        Assert.assertEquals(pageable, captorPageable.getValue());

        Assert.assertEquals(1, actual.getTotalPages());
        Assert.assertEquals(expected.size(), actual.getTotalElements());
    }

    @Test
    public void testListFirstPageOfTransactionsOrderByValueDateDesc() {
        String accountNumber = "1001";
        Pageable pageable = PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "valueDate"));

        Account account = new Account(accountNumber, "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 12), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);

        List<Transaction> expected = new ArrayList<>();
        expected.add(t1);
        expected.add(t2);

        Page<Transaction> foundPage = new PageImpl<>(expected);

        when(repo.findByAccountNumber(anyString(), any(Pageable.class))).thenReturn(foundPage);

        Page<Transaction> actual = service.listFirstPageOfTransactionsOrderByValueDateDesc(accountNumber, 30);

        ArgumentCaptor<String> captorAccount = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> captorPage = ArgumentCaptor.forClass(Pageable.class);
        verify(repo).findByAccountNumber(captorAccount.capture(), captorPage.capture());

        Assert.assertEquals(accountNumber, captorAccount.getValue());
        Assert.assertEquals(pageable, captorPage.getValue());
        Assert.assertEquals(1, actual.getTotalPages());
        Assert.assertEquals(expected.size(), actual.getTotalElements());
    }

}
