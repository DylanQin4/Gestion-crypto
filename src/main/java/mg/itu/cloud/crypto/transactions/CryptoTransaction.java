package mg.itu.cloud.crypto.transactions;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import mg.itu.cloud.fund.TransactionType;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "crypto_transactions")
public class CryptoTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crypto_transactions_id_gen")
    @SequenceGenerator(name = "crypto_transactions_id_gen", sequenceName = "crypto_transactions_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @NotNull
    @Column(name = "cryptocurrency_id", nullable = false)
    private Integer cryptoId;

    @NotNull
    @Column(name = "quantity", nullable = false, precision = 18, scale = 8)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "price_unit", nullable = false, precision = 18, scale = 8)
    private BigDecimal priceUnit;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 18, scale = 8)
    private BigDecimal totalAmount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "transaction_date")
    private Instant transactionDate;

    public CryptoTransaction() {
    }

    public CryptoTransaction(Integer id, Integer userId, TransactionType transactionType, Integer cryptoId, BigDecimal quantity, BigDecimal priceUnit, BigDecimal totalAmount, Instant transactionDate) {
        this.id = id;
        this.userId = userId;
        this.transactionType = transactionType;
        this.cryptoId = cryptoId;
        this.quantity = quantity;
        this.priceUnit = priceUnit;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return userId;
    }

    public void setUser(Integer userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getCryptocurrency() {
        return cryptoId;
    }

    public void setCryptocurrency(Integer cryptoId) {
        this.cryptoId = cryptoId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(BigDecimal priceUnit) {
        this.priceUnit = priceUnit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }
  public String getFormattedDate() {
        if (transactionDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return transactionDate.atZone(ZoneId.systemDefault()).format(formatter);
        }
        return null;
    }


}