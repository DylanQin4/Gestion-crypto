package mg.itu.cloud.crypto;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cryptocurrency_id", nullable = false)
    private Integer cryptocurrencyId;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal open;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal high;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal low;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal close;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal change;

    private LocalDateTime recordDate = LocalDateTime.now();

    @PrePersist // Definit recordDate a la date actuelle avant insertion
    public void prePersist() {
        this.recordDate = LocalDateTime.now();
    }

    public PriceHistory() {}

    public PriceHistory(Integer cryptocurrencyId, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal change) {
        this.cryptocurrencyId = cryptocurrencyId;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.change = change;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCryptocurrencyId() {
        return cryptocurrencyId;
    }

    public void setCryptocurrencyId(Integer cryptocurrencyId) {
        this.cryptocurrencyId = cryptocurrencyId;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }
}

