package mg.itu.cloud.wallet;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "fund_balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal fundBalance = BigDecimal.ZERO;

    public Wallet() {
    }

    public Wallet(Long userId, BigDecimal fundBalance) {
        this.userId = userId;
        this.fundBalance = fundBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getFundBalance() {
        return fundBalance;
    }

    public void setFundBalance(BigDecimal fundBalance) {
        this.fundBalance = fundBalance;
    }
}
