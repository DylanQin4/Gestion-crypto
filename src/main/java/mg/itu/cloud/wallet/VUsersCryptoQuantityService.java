package mg.itu.cloud.wallet;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        List<VUsersCryptoQuantity> vUsersCryptoQuantities = new ArrayList<>();
        vUsersCryptoQuantityReposiitory.findByUserId(userId).forEach(vUsersCryptoQuantity -> {
            if (vUsersCryptoQuantity.getTotalQuantity().compareTo(BigDecimal.ZERO) != 0) {
                vUsersCryptoQuantities.add(vUsersCryptoQuantity);
            }
        });
        return vUsersCryptoQuantities;
    }
}
