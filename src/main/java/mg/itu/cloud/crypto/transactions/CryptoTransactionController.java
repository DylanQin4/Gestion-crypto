package mg.itu.cloud.crypto.transactions;

import jakarta.servlet.http.HttpSession;
import mg.itu.cloud.crypto.CryptoService;
import mg.itu.cloud.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/crypto/transactions")
public class CryptoTransactionController {
    private final CryptoService cryptoService;
    private final CryptoTransactionService cryptoTransactionService;

    public CryptoTransactionController(CryptoService cryptoService, CryptoTransactionService cryptoTransactionService) {
        this.cryptoService = cryptoService;
        this.cryptoTransactionService = cryptoTransactionService;
    }

    @GetMapping
    public String getTransactions(Model model) {
        model.addAttribute("allCrypto", cryptoService.getAllCrypto());
        return "pages/crypto-transactions";
    }

    @PostMapping
    public String processTransaction(@RequestParam String action,
                                     @RequestParam Integer cryptocurrencyId,
                                     @RequestParam BigDecimal quantity,
                                     RedirectAttributes redirectAttributes,
                                     @SessionAttribute(name = "user", required = false) User user) {
        if (user == null) {
            return "redirect:/auth/login";
        }
        Integer userId = user.getId();
        try {
            if ("buy".equals(action)) {
                cryptoTransactionService.buy(userId, cryptocurrencyId, quantity);
                redirectAttributes.addFlashAttribute("message", "Achat effectué avec succès.");
            } else if ("sell".equals(action)) {
                cryptoTransactionService.sell(userId, cryptocurrencyId, quantity);
                redirectAttributes.addFlashAttribute("message", "Vente effectuée avec succès.");
            }
        } catch (IllegalArgumentException iae) {
            redirectAttributes.addFlashAttribute("error", "Erreur");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/crypto/transactions";
    }
}
