package mg.itu.cloud.crypto;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CryptoPriceScheduler {

    private final PriceHistoryService priceHistoryService;

    public CryptoPriceScheduler(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @Scheduled(fixedRate = 10000) // 🔄 Toutes les 10 secondes
    public void schedulePriceGeneration() {
        priceHistoryService.generatePrices();
        System.out.println("📈 Prix des cryptos mis à jour : " + LocalDateTime.now());
    }
}
