package mg.itu.cloud.wallet;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "v_users_crypto_quantity")
public class VUsersCryptoQuantity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "crypto_id")
    private Integer cryptoId;

    @Column(name = "crypto_name")
    private String cryptoName;

    @Column(name = "crypto_symbol")
    private String cryptoSymbol;

    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;

    public Integer getUserId() {
        return userId;
    }

    public Integer getCryptoId() {
        return cryptoId;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    protected VUsersCryptoQuantity() {
    }
}