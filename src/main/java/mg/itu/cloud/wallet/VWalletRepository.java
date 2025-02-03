package mg.itu.cloud.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VWalletRepository extends JpaRepository<VWallet, Integer> {

}
