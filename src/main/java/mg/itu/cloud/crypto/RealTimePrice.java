package mg.itu.cloud.crypto;

import java.math.BigDecimal;

public record RealTimePrice(
        Crypto cryptl,
        Integer IdPriceHistory,
        BigDecimal price,
        BigDecimal change
) {
}
