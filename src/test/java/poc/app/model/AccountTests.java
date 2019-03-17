package poc.app.model;

import poc.app.model.Account;
import poc.app.model.Transaction;
import poc.app.model.TransactionType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountTests {


    @Test
    public void testOpeningBalanceCalculation() {
        Account account = new Account();
        Transaction t1 = new Transaction(account, null, null, new BigDecimal("100.50"), TransactionType.Credit, null);
        Transaction t2 = new Transaction(account, null, null, new BigDecimal("100.25"), TransactionType.Debit, null);
        Transaction t3 = new Transaction(account, null, null, new BigDecimal("10"), TransactionType.Debit, null);
        Transaction t4 = new Transaction(account, null, null, new BigDecimal("10"), TransactionType.Credit, null);

        account.addTransaction(t1);
        account.addTransaction(t2);
        account.addTransaction(t3);
        account.addTransaction(t4);

        BigDecimal expected = new BigDecimal("0.25");
        Assert.assertEquals(expected, account.getOpeningAvailableBalance());

    }
}