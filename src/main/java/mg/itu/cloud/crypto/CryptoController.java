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
import java.time.LocalDate;
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

    @GetMapping("/price/{idCrypto}")
    @ResponseBody
    public ResponseEntity<String> getCryptoPrice(@PathVariable Integer idCrypto) {
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

    @GetMapping("/evolution/{idCrypto}")
    public String getCryptoGraph(@PathVariable Integer idCrypto, Model model) {
        return "pages/crypto-graphe";
    }

    @GetMapping("/evolution/data/{idCrypto}")
    @ResponseBody
    public List<Map<String, Object>> getEvolutionData(@PathVariable Integer idCrypto) {
        List<Map<String, Object>> candleData = generateCandleData(50);
        return candleData; // Cela renverra les données en JSON
    }

    private List<Map<String, Object>> generateCandleData(int numberOfEntries) {
        List<Map<String, Object>> candleData = new ArrayList<>();
        Random random = new Random();
        LocalDate startDate = LocalDate.now().minusDays(numberOfEntries); // Commence à partir d'aujourd'hui moins le nombre d'entrées

        // Initialiser la première valeur d'ouverture
        int open = random.nextInt(10000) + 13000; // Valeur d'ouverture aléatoire entre 13000 et 23000

        for (int i = 0; i < numberOfEntries; i++) {
            LocalDate date = startDate.plusDays(i);

            // Générer la valeur de clôture, basse et haute
            int close = open + random.nextInt(1000) - 500; // Valeur de clôture aléatoire
            int low = Math.min(open, close) - random.nextInt(500); // Valeur basse
            int high = Math.max(open, close) + random.nextInt(500); // Valeur haute

            // Ajouter les données du chandelier
            candleData.add(Map.of(
                    "x", date.format(DateTimeFormatter.ISO_DATE), // Format de date ISO
                    "y", List.of(open, high, low, close) // Liste des valeurs [open, high, low, close]
            ));

            // La valeur d'ouverture du prochain chandelier est la valeur de clôture actuelle
            open = close; // Mettre à jour l'ouverture pour le prochain chandelier
        }

        return candleData;
    }


}
