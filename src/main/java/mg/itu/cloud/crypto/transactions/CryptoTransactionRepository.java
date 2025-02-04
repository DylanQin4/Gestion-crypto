package mg.itu.cloud.crypto.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoTransactionRepository extends JpaRepository<CryptoTransaction, Integer> {
}
