package poc.app.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(generator = "TXN_SEQ")
    @SequenceGenerator(name = "TXN_SEQ", sequenceName = "TXN_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    @Transient
    @JsonProperty("accountName")
    private String txnAccountName;

    @Transient
    @JsonProperty("accountNumber")
    private String txnAccountNumber;

    private LocalDate valueDate;

    // TODO: consider to use Javamoney
    private String currency;

    private BigDecimal amount = BigDecimal.ZERO;

    private TransactionType type;

    private String narrative;

    protected Transaction() {
    }

    public Transaction(Account account, LocalDate valueDate, String currency, BigDecimal amount, TransactionType type, String narrative) {
        this.account = account;
        this.valueDate = valueDate;
        this.currency = currency;
        this.amount = amount;
        this.type = type;
        this.narrative = narrative;
    }

    public Account getAccount() {
        return account;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getNarrative() {
        return narrative;
    }

    @JsonGetter("valueDate")
    public String getValueDateAsString() {
        return valueDate.toString();
    }

    public Long getId() {
        return id;
    }

    public String getTxnAccountName() {
        return account.getName();
    }

    public String getTxnAccountNumber() {
        return account.getNumber();
    }
}
