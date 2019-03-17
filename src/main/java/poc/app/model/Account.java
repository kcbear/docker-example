package poc.app.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {


    @Id
    @GeneratedValue(generator = "ACCT_SEQ")
    @SequenceGenerator(name = "ACCT_SEQ", sequenceName = "ACCT_SEQ", allocationSize = 1)
    private Long id;

    private String number;

    private String name;

    private AccountType type;

    private LocalDate balanceDate;

    // TODO: Use Javamoney, considering currency fraction digits
    private String currency;

    private BigDecimal openingAvailableBalance = BigDecimal.ZERO;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    protected Account() {
    }

    public Account(String number, String name, AccountType type, LocalDate balanceDate, String currency) {
        this.number = number;
        this.name = name;
        this.type = type;
        this.balanceDate = balanceDate;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public LocalDate getBalanceDate() {
        return balanceDate;
    }

    @JsonGetter("balanceDate")
    public String getBalanceDateAsString() {
        return balanceDate.toString();
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getOpeningAvailableBalance() {
        return openingAvailableBalance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // TODO: revisit, should update opening balance
    public void addTransaction(Transaction txn) {
        if (txn.getType() == TransactionType.Debit) {
            openingAvailableBalance = openingAvailableBalance.subtract(txn.getAmount());
        } else {
            openingAvailableBalance = openingAvailableBalance.add(txn.getAmount());
        }

        transactions.add(txn);
    }

}
