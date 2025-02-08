package mg.itu.cloud.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VUsersCryptoQuantityReposiitory extends JpaRepository<VUsersCryptoQuantity, Integer> {
    List<VUsersCryptoQuantity> findByUserId(Integer userId);

    @Query("SELECT totalQuantity FROM VUsersCryptoQuantity WHERE userId = ?1 AND cryptoId = ?2")
    BigDecimal getTotalQuantityByUserIdAndCryptoId(Integer userId, Integer cryptoId);
}
