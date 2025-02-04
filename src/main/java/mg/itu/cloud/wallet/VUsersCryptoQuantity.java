package mg.itu.cloud.wallet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "v_users_crypto_quantity")
public class VUsersCryptoQuantity {
    @Id
    Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "crypto_id")
    private Integer cryptoId;

    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;

    public Integer getUserId() {
        return userId;
    }

    public Integer getCryptoId() {
        return cryptoId;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    protected VUsersCryptoQuantity() {
    }
}