package mg.itu.cloud.fund;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "fund_transactions")
public class FundTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fund_transactions_id_gen")
    @SequenceGenerator(name = "fund_transactions_id_gen", sequenceName = "fund_transactions_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @Size(max = 10)
    @ColumnDefault("'PENDING'")
    @Column(nullable = false, name = "status", length = 10)
    private String status = Status.PENDING.name();

    @NotNull
    @Column(name = "amount", nullable = false, precision = 18, scale = 8)
    private BigDecimal amount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "transaction_date")
    private Instant transactionDate = Instant.now();

    public FundTransaction() {
    }

    public FundTransaction(Integer userId, TransactionType transactionType, String status, BigDecimal amount, Instant transactionDate) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.status = status;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public FundTransaction(Integer id, Integer userId, TransactionType transactionType, String status, BigDecimal amount, Instant transactionDate) {
        this.id = id;
        this.userId = userId;
        this.transactionType = transactionType;
        this.status = status;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

}