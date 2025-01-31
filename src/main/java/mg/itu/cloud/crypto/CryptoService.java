package mg.itu.cloud.crypto;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CryptoService {
    private final CryptoRepository cryptoRepository;
    private final PriceHistoryService priceHistoryService;

    public CryptoService(CryptoRepository cryptoRepository, PriceHistoryService priceHistoryService) {
        this.cryptoRepository = cryptoRepository;
        this.priceHistoryService = priceHistoryService;
    }

    public List<RealTimePrice> getAllCryptoWithRealTimePrice() {
        List<Crypto> allCrypto = getAllCrypto();

        List<RealTimePrice> allCryptoWithRealTimePrice = new ArrayList<>();

        for (Crypto crypto : allCrypto) {
            allCryptoWithRealTimePrice.add(priceHistoryService.calculateRealTimePrice(crypto.getId()));
        }
        return allCryptoWithRealTimePrice;
    }

    public List<Crypto> getAllCrypto() {
        return cryptoRepository.findAll();
    }

    public Optional<Crypto> getCryptoById(Integer idCrypto) {
        return cryptoRepository.findById(idCrypto);
    }
}
