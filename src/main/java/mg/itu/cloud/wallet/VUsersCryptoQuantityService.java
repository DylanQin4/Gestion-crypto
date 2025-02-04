package mg.itu.cloud.wallet;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VUsersCryptoQuantityService {
    private final VUsersCryptoQuantityReposiitory vUsersCryptoQuantityReposiitory;

    public VUsersCryptoQuantityService(VUsersCryptoQuantityReposiitory vUsersCryptoQuantityReposiitory) {
        this.vUsersCryptoQuantityReposiitory = vUsersCryptoQuantityReposiitory;
    }

    public BigDecimal getTotalQuantityByUserIdAndCryptoId(Integer userId, Integer cryptoId) {
        return vUsersCryptoQuantityReposiitory.getTotalQuantityByUserIdAndCryptoId(userId, cryptoId);
    }

    public List<VUsersCryptoQuantity> getAllUsersCrypto(Integer userId) {
        return vUsersCryptoQuantityReposiitory.findByUserId(userId);
    }
}
