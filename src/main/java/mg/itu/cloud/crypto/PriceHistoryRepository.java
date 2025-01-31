package mg.itu.cloud.crypto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {

    @Query("SELECT p FROM PriceHistory p WHERE p.cryptocurrencyId = :cryptoId ORDER BY p.recordDate DESC")
    List<PriceHistory> findByCryptocurrencyId(@Param("cryptoId") Integer cryptoId);

    @Query("SELECT p FROM PriceHistory p WHERE p.cryptocurrencyId = :cryptoId ORDER BY p.recordDate DESC LIMIT 2")
    List<PriceHistory> findLastTwoPricesByCryptocurrencyId(@Param("cryptoId") Integer cryptoId);

    @Query("SELECT p FROM PriceHistory p WHERE p.id > :priceHistoryId AND p.cryptocurrencyId = :cryptoId ORDER BY p.id ASC LIMIT 1")
    Optional<PriceHistory> findNextPriceById(
            @Param("priceHistoryId") Integer priceHistoryId,
            @Param("cryptoId") Integer cryptoId
    );
}
