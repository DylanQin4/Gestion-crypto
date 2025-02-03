package mg.itu.cloud.wallet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "v_wallet")
public class VWallet {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "total_funds_amount")
    private BigDecimal totalFundsAmount;

    @Column(name = "total_sales_amount")
    private BigDecimal totalSalesAmount;

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getTotalFundsAmount() {
        return totalFundsAmount;
    }

    public BigDecimal getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public BigDecimal getFundBalance() {
        return totalFundsAmount.add(totalSalesAmount);
    }

    protected VWallet() {
    }
}