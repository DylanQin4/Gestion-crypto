package mg.itu.cloud.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserCryptoTransactionController {

    @Autowired
    private UserCryptoTransactionService service;

    // Endpoint pour afficher les transactions de type "BUY" et "SELL"
    @GetMapping("/user-crypto-transactions")
    public String showUserCryptoTransactions(Model model) {
        List<UserCryptoTransaction> transactions = service.getBuyAndSellTransactions();
        model.addAttribute("transactions", transactions);
        model.addAttribute("message", "Affichage des transactions réussi.");
        model.addAttribute("error", null);
        return "pages/userTransactions"; 
    }
}
