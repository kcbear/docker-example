package poc.app.service;

import poc.app.model.Account;
import poc.app.model.AccountType;
import poc.app.repository.AccountRepository;
import poc.app.service.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AccountServiceTests {

    private AccountRepository repo = mock(AccountRepository.class);

    private AccountService service = new AccountService(repo);

    @Test
    public void testListAccountsReturnNothing() {
        when(repo.findAll()).thenReturn(new ArrayList<>());

        List<Account> actual = service.listAccounts();

        verify(repo).findAll();

        Assert.assertEquals(0, actual.size());

    }

    @Test
    public void testListAccountsReturnSomeAccounts() {
        Account account1 = new Account("1001", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");
        Account account2 = new Account("1002", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 10, 2), "SGD");

        List<Account> expected = new ArrayList<>();
        expected.add(account1);
        expected.add(account2);

        when(repo.findAll()).thenReturn(expected);

        List<Account> actual = service.listAccounts();

        verify(repo).findAll();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testListAccountsOrderByField() {
        String orderBy = "balanceDate";

        Account account1 = new Account("1001", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");
        Account account2 = new Account("1002", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 10, 2), "SGD");

        List<Account> expected = new ArrayList<>();
        expected.add(account1);
        expected.add(account2);

        when(repo.findAll(any(Sort.class))).thenReturn(expected);

        List<Account> actual = service.listAccountsOrderByField(Sort.Direction.DESC, orderBy);

        ArgumentCaptor<Sort> captorSort = ArgumentCaptor.forClass(Sort.class);

        verify(repo).findAll(captorSort.capture());

        Assert.assertEquals(Sort.by(Sort.Direction.DESC, orderBy), captorSort.getValue());
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testListAccountsWithPage() {

        Pageable pageable = PageRequest.of(0, 2);

        Account account1 = new Account("1001", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");
        Account account2 = new Account("1002", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 10, 2), "SGD");

        List<Account> expected = new ArrayList<>();
        expected.add(account1);
        expected.add(account2);

        Page<Account> foundPage = new PageImpl<>(expected);

        when(repo.findAll(any(Pageable.class))).thenReturn(foundPage);

        Page<Account> actual = service.listAccountsWithPage(pageable);


        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(repo).findAll(captor.capture());

        Assert.assertEquals(pageable, captor.getValue());
        Assert.assertEquals(1, actual.getTotalPages());
        Assert.assertEquals(2, actual.getTotalElements());
    }

    @Test
    public void testListFirstPageOfAccountsOrderByBalanceDateDesc() {

        Pageable pageable = PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "balanceDate"));

        Account account1 = new Account("1001", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");
        Account account2 = new Account("1002", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 10, 2), "SGD");

        List<Account> expected = new ArrayList<>();
        expected.add(account1);
        expected.add(account2);

        Page<Account> foundPage = new PageImpl<>(expected);

        when(repo.findAll(any(Pageable.class))).thenReturn(foundPage);

        Page<Account> actual = service.listFirstPageOfAccountsOrderByBalanceDateDesc(30);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(repo).findAll(captor.capture());

        Assert.assertEquals(pageable, captor.getValue());
        Assert.assertEquals(1, actual.getTotalPages());
        Assert.assertEquals(expected.size(), actual.getTotalElements());

    }


}
