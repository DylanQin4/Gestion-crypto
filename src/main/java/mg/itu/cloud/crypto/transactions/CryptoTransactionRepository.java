package mg.itu.cloud.crypto.transactions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CryptoTransactionRepository extends JpaRepository<CryptoTransaction, Integer> {
    List<CryptoTransaction> findByUserId(Integer userId);
}
