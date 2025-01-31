package mg.itu.cloud.crypto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;
    private final CryptoRepository cryptoRepository;
    private final Random random = new Random();

    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository, CryptoRepository cryptoRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.cryptoRepository = cryptoRepository;
    }

    public List<PriceHistory> getAllPricesForCrypto(Integer cryptoId) {
        return priceHistoryRepository.findByCryptocurrencyId(cryptoId);
    }

    public void generatePrices() {
        List<Crypto> cryptocurrencies = cryptoRepository.findAll();

        for (Crypto crypto : cryptocurrencies) {
            // Recuperer le dernier prix de cette cryptomonnaie
            Optional<PriceHistory> lastPriceOpt = priceHistoryRepository
                    .findLastPricesByCryptocurrencyId(crypto.getId());

            BigDecimal open;
            BigDecimal maxVariation;

            if (lastPriceOpt.isPresent()) {
                PriceHistory lastPrice = lastPriceOpt.get();
                open = lastPrice.getClose();  // Nouveau open = ancien close

                // Calculer la variation max (±200% de la difference Open/Close precedente)
                BigDecimal lastVariation = lastPrice.getClose().subtract(lastPrice.getOpen()).abs();
                maxVariation = lastVariation.multiply(BigDecimal.valueOf(2)); // 200%

            } else {
                // Cas ou aucun historique n'existe (premier prix arbitraire)
                open = BigDecimal.valueOf(100);  // Prix de depart par defaut
                maxVariation = BigDecimal.valueOf(10);  // Variation max par defaut
            }

            // Generer une variation aleatoire dans la limite de maxVariation
            BigDecimal variation = BigDecimal.valueOf(
                    (random.nextDouble() * 2 - 1) * maxVariation.doubleValue()  // Entre -maxVariation et +maxVariation
            );

            BigDecimal close = open.add(variation);
            BigDecimal high = close.add(BigDecimal.valueOf(random.nextDouble() * maxVariation.doubleValue() / 2));
            BigDecimal low = close.subtract(BigDecimal.valueOf(random.nextDouble() * maxVariation.doubleValue() / 2));

            // S'assurer que les valeurs restent logiques
            if (low.compareTo(BigDecimal.ZERO) < 0) low = BigDecimal.ZERO;

            PriceHistory price = new PriceHistory();
            price.setCryptocurrencyId(crypto.getId());
            price.setOpen(open);
            price.setHigh(high.max(open));  // high ne doit pas être < open
            price.setLow(low.min(open));  // low ne doit pas être > open
            price.setClose(close);
            price.setChange(close.subtract(open));
            price.setRecordDate(LocalDateTime.now());

            priceHistoryRepository.save(price);
        }
    }

    public PriceHistory getLastPriceBy(Integer cryptoId) {
        return priceHistoryRepository.findLastPricesByCryptocurrencyId(cryptoId).orElse(null);
    }

    public Optional<PriceHistory> getNextPriceBy(Integer priceHistoryId, Integer cryptoId) {
        return priceHistoryRepository.findNextPriceById(priceHistoryId, cryptoId);
    }
}

