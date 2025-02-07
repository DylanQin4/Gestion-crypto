package mg.itu.cloud.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCryptoTransactionRepository extends JpaRepository<UserCryptoTransaction, Integer> {
    List<UserCryptoTransaction> findByUserName(String userName);
    List<UserCryptoTransaction> findByTransactionTypeIn(List<String> transactionTypes);
}
