package mg.itu.cloud.wallet;

import java.math.BigDecimal;
import java.util.List;

public record WalletResponse(
        BigDecimal totalFundsAmount,
        List<VUsersCryptoQuantity> usersCryptoQuantityList
) {
}
