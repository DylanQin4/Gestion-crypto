package mg.itu.cloud.crypto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;
    private final CryptoRepository cryptoRepository;

    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository, CryptoRepository cryptoRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.cryptoRepository = cryptoRepository;
    }

    public List<PriceHistory> getAllPricesForCrypto(Integer cryptoId) {
        return priceHistoryRepository.findByCryptocurrencyId(cryptoId);
    }

    public RealTimePrice calculateRealTimePrice(Integer cryptoId) {
        // Récupérer les 2 derniers prix
        List<PriceHistory> prices = priceHistoryRepository.findLastTwoPricesByCryptocurrencyId(cryptoId);

        // Dernier prix et prix precedent
        PriceHistory latestPrice;
        PriceHistory previousPrice;
        if (prices.isEmpty()) {
            throw new IllegalArgumentException("Il n'y a pas de données historiques");
        } else if (prices.size() < 2) {
            latestPrice = prices.get(0);
            previousPrice = prices.get(0);
        } else {
            latestPrice = prices.get(0);
            previousPrice = prices.get(1);
        }

        // Calcul du change
        BigDecimal lastPrice = latestPrice.getClose();
        BigDecimal prevPrice = previousPrice.getClose();

        // Calcul du pourcentage de changement
        if (prevPrice.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Le prix précédent ne peut pas être zéro.");
        }

        BigDecimal change = (lastPrice.subtract(prevPrice))
                .divide(prevPrice, 8, RoundingMode.HALF_UP) // Division avec précision
                .multiply(BigDecimal.valueOf(100)); // Multiplier par 100 pour obtenir le pourcentage

        Crypto crypto = cryptoRepository.findById(cryptoId).orElseThrow();

        // Retourner un objet RealTimePrice avec les valeurs necessaires
        return new RealTimePrice(
                crypto,
                latestPrice.getId(),
                latestPrice.getClose().setScale(4, RoundingMode.FLOOR),
                change.setScale(2, RoundingMode.FLOOR)
        );
    }



    public Optional<PriceHistory> getNextPriceById(Integer priceHistoryId, Integer cryptoId) {
        return priceHistoryRepository.findNextPriceById(priceHistoryId, cryptoId);
    }
}

