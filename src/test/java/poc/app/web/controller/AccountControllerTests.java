package poc.app.web.controller;


import poc.app.model.Account;
import poc.app.model.AccountType;
import poc.app.model.Transaction;
import poc.app.model.TransactionType;
import poc.app.service.AccountService;
import poc.app.service.TransactionService;
import poc.app.web.controller.AccountController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void testListAccountsReturnNothing() throws Exception {
        when(accountService.listAccounts()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/accounts")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
        verify(accountService).listAccounts();
    }

    @Test
    public void testListAccountsReturnSomething() throws Exception {
        Account account1 = new Account("1001", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");
        Account account2 = new Account("1002", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 10, 2), "SGD");

        List<Account> expected = new ArrayList<>();
        expected.add(account1);
        expected.add(account2);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(expected);

        when(accountService.listAccounts()).thenReturn(expected);

        this.mockMvc.perform(get("/accounts")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
        verify(accountService).listAccounts();
    }

    @Test
    public void testListTransactionsReturnNothing() throws Exception {
        when(transactionService.listTransactionsByAccountNumber(anyString())).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/transactions/1001")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));


        ArgumentCaptor<String> captorAccount = ArgumentCaptor.forClass(String.class);
        verify(transactionService).listTransactionsByAccountNumber(captorAccount.capture());

        Assert.assertEquals("1001", captorAccount.getValue());
    }

    @Test
    public void testListTransactionsReturnSomething() throws Exception {

        Account account = new Account("1001", "SGSaving726", AccountType.Savings, LocalDate.of(2018, 12, 1), "SGD");

        Transaction t1 = new Transaction(account, LocalDate.of(2019, 2, 12), "SGD", new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, LocalDate.of(2019, 2, 11), "SGD", new BigDecimal("100.25"), TransactionType.Debit, null);

        List<Transaction> expected = new ArrayList<>();
        expected.add(t1);
        expected.add(t2);


        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(expected);

        when(transactionService.listTransactionsByAccountNumber(anyString())).thenReturn(expected);

        this.mockMvc.perform(get("/transactions/1001")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(expectedJson));

        ArgumentCaptor<String> captorAccount = ArgumentCaptor.forClass(String.class);
        verify(transactionService).listTransactionsByAccountNumber(captorAccount.capture());

        Assert.assertEquals("1001", captorAccount.getValue());
    }
}