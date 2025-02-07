package mg.itu.cloud.crypto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    public List<Map<String, Object>> getAllPricesForCryptoAsMap(Integer cryptoId) {
        List<PriceHistory> priceHistories = priceHistoryRepository.findByCryptocurrencyId(cryptoId);
        return priceHistories.stream().map(this::convertPriceHistoryToMap).toList();
    }

    public Map<String, Object> convertPriceHistoryToMap(PriceHistory priceHistory) {
        return Map.of(
                "id", priceHistory.getId(),
                "open", priceHistory.getOpen(),
                "high", priceHistory.getHigh(),
                "low", priceHistory.getLow(),
                "close", priceHistory.getClose(),
                "change", priceHistory.getChange(),
                "recordDate", priceHistory.getRecordDate()
        );
    }

    public void generatePrices() {
        List<Crypto> cryptocurrencies = cryptoRepository.findAll();

        for (Crypto crypto : cryptocurrencies) {
            Optional<PriceHistory> lastPriceOpt = priceHistoryRepository.findLastPricesByCryptocurrencyId(crypto.getId());

            BigDecimal open;
            BigDecimal volatility; // Volatilite en valeur absolue

            if (lastPriceOpt.isPresent()) {
                PriceHistory lastPrice = lastPriceOpt.get();
                open = lastPrice.getClose().max(BigDecimal.valueOf(0.01)); // Assurer un prix positif

                // Definir la volatilite en fonction du prix actuel
                volatility = open.multiply(BigDecimal.valueOf(0.05)); // 5% du prix actuel
            } else {
                // Generer un prix initial aleatoire entre 10 et 1000
                open = BigDecimal.valueOf(10 + (random.nextDouble() * 990)); // Entre 10 et 1000

                // Definir la volatilite initiale
                volatility = open.multiply(BigDecimal.valueOf(0.05)); // 5% du prix initial
            }

            // Generer une variation à partir d'une distribution normale
            double mean = 0.01 * open.doubleValue(); // Legère tendance à la hausse (1% du prix)
            double stdDev = volatility.doubleValue(); // Volatilite
            double variation = mean + random.nextGaussian() * stdDev;

            // Appliquer la variation au prix
            BigDecimal close = open.add(BigDecimal.valueOf(variation)).max(BigDecimal.valueOf(0.01)); // Assurer que close est positif

            // ✅ Calculer les bornes (high et low)
            BigDecimal high = close.add(BigDecimal.valueOf(random.nextDouble() * stdDev / 2))
                    .max(open); // high >= open

            BigDecimal low = close.subtract(BigDecimal.valueOf(random.nextDouble() * stdDev / 2))
                    .min(open) // low <= open
                    .max(BigDecimal.valueOf(0.01)); // low ≥ 0.01

            // ✅ Assurer que close reste bien entre low et high
            close = close.max(low).min(high);

            // Enregistrer le nouveau prix
            PriceHistory price = new PriceHistory();
            price.setCryptocurrencyId(crypto.getId());
            price.setOpen(open);
            price.setHigh(high);
            price.setLow(low);
            price.setClose(close);
            price.setChange(close.subtract(open));
            price.setRecordDate(LocalDateTime.now());

            priceHistoryRepository.save(price);
        }
    }

    public PriceHistory getLastPriceBy(Integer cryptoId) {
        return priceHistoryRepository.findLastPricesByCryptocurrencyId(cryptoId)
                .orElse(new PriceHistory(null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
        );
    }

    public Optional<PriceHistory> getNextPriceBy(Integer priceHistoryId, Integer cryptoId) {
        return priceHistoryRepository.findNextPriceById(priceHistoryId, cryptoId);
    }
}

