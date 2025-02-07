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
        // Récupérer les transactions de type "BUY" et "SELL"
        List<UserCryptoTransaction> transactions = service.getBuyAndSellTransactions();

        // Ajouter la liste des transactions au modèle pour l'affichage
        model.addAttribute("transactions", transactions);

        // Ajouter un message de succès ou d'erreur
        model.addAttribute("message", "Affichage des transactions réussi.");
        model.addAttribute("error", null);

        // Retourner le nom de la page JTE
        return "pages/userTransactions"; 
    }
}
