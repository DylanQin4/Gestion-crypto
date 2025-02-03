package mg.itu.cloud.crypto;

import mg.itu.cloud.Config.AppConstants;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class CryptoPriceScheduler {

    private final PriceHistoryService priceHistoryService;

    public CryptoPriceScheduler(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @Scheduled(fixedRate = AppConstants.PRICE_GENERATION_INTERVAL)
    public void schedulePriceGeneration() {
        priceHistoryService.generatePrices();
        System.out.println("📈 Prix des cryptos mis à jour : " + LocalDateTime.now());
    }
}
