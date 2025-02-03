package mg.itu.cloud.crypto;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("crypto")
public class CryptoController {
    private final CryptoService cryptoService;
    private final PriceHistoryService priceHistoryService;

    public CryptoController(CryptoService cryptoService, PriceHistoryService priceHistoryService) {
        this.cryptoService = cryptoService;
        this.priceHistoryService = priceHistoryService;
    }

    @GetMapping
    public String getRealTimePrices(Model model) {
        model.addAttribute("allCrypto", cryptoService.getAllCrypto());
        return "pages/crypto-prices";
    }

    @GetMapping("/last-price/{idCrypto}")
    @ResponseBody
    public ResponseEntity<String> getCryptoLastPrice(@PathVariable Integer idCrypto) {
        Optional<Crypto> crypto = cryptoService.getCryptoById(idCrypto);
        if (crypto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PriceHistory lastPrice = priceHistoryService.getLastPriceBy(idCrypto);
        if (lastPrice == null) {
            return ResponseEntity.notFound().build();
        }

        Map<@NotNull String, ? extends Serializable> priceData = Map.of(
                "crypto", crypto.get().getId(),
                "price", lastPrice.getClose().toString(),
                "change", lastPrice.getChange().toString(),
                "date", lastPrice.getRecordDate().format(DateTimeFormatter.ISO_DATE_TIME)
        );

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String priceDataJson = objectMapper.writeValueAsString(priceData);
            return ResponseEntity.ok(priceDataJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/prices/{idCrypto}")
    @ResponseBody
    public List<Map<String, Object>> getPricesByCrypto(@PathVariable Integer idCrypto, Model model) {
        Optional<Crypto> crypto = cryptoService.getCryptoById(idCrypto);
        if (crypto.isEmpty()) {
            return null;
        }
        return priceHistoryService.getAllPricesForCryptoAsMap(idCrypto);
    }

    @GetMapping("/price/{idCrypto}/{idPriceHistory}")
    @ResponseBody
    public Map<String, Object> getNextPriceByIdPriceHistory(@PathVariable Integer idCrypto, @PathVariable Integer idPriceHistory, Model model) {
        Optional<PriceHistory> priceHistory = priceHistoryService.getNextPriceBy(idPriceHistory, idCrypto);
        return priceHistory.map(priceHistoryService::convertPriceHistoryToMap).orElse(null);
    }

    @GetMapping("/evolution/{idCrypto}")
    public String getCryptoGraph(@PathVariable Integer idCrypto, Model model) {
        Optional<Crypto> crypto = cryptoService.getCryptoById(idCrypto);
        if (crypto.isEmpty()) {
            return "redirect:/crypto";
        }
        model.addAttribute("id", idCrypto);
        model.addAttribute("crypto", crypto.get());
        return "pages/crypto-graphe";
    }

}
