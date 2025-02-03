package mg.itu.cloud.fund;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {
    Optional<TransactionType> findByName(@Size(max = 20) @NotNull String name);
}
