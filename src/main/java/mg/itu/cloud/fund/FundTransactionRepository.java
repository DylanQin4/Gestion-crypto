package mg.itu.cloud.fund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundTransactionRepository extends JpaRepository<FundTransaction, Integer> {
    @Query("SELECT ft FROM FundTransaction ft WHERE ft.userId = :userId ORDER BY ft.transactionDate DESC")
    List<FundTransaction> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT ft FROM FundTransaction ft WHERE ft.status = :name")
    List<FundTransaction> findByStatus(String name);
}