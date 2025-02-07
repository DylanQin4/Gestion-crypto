package mg.itu.cloud.fund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/fund-management")
public class TransactionController {

    @Autowired
    private FundTransactionService fundTransactionService;

    @GetMapping
    public String afficherPageFonds(Model model) {
        Integer userId = 1;

        List<FundTransaction> transactions = fundTransactionService.getFundTransactionHistory(userId);

        model.addAttribute("transactions", transactions);
        return "pages/fund";
    }

       
    @GetMapping("/all")
    public String afficherToutesLesTransactions(Model model) {
        List<FundTransaction> transactions = fundTransactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        model.addAttribute("message", "Historique des transactions récupéré avec succès !");
        model.addAttribute("error", "Erreur lors de la récupération des transactions.");
        return "pages/fund_all"; // afficher toutes les transactions
    }

    @PostMapping("/deposit")
    public String deposer(@RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        Integer userId = 1;
        try {
            fundTransactionService.deposit(userId, amount);
            redirectAttributes.addFlashAttribute("message", "Dépôt effectué avec succès.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors du dépôt.");
        }
        return "redirect:/fund-management";
    }

    @PostMapping("/withdraw")
    public String retirer(@RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        Integer userId = 1;
        try {
            fundTransactionService.withdraw(userId, amount);
            redirectAttributes.addFlashAttribute("message", "Retrait effectué avec succès.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors du retrait.");
        }
        return "redirect:/fund-management";
    }
        @PostMapping("/buy")
    public String acheter(@RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        Integer userId = 1;
        try {
            fundTransactionService.buy(userId, amount);
            redirectAttributes.addFlashAttribute("message", "achat.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur d'achat.");
        }
        return "redirect:/fund-management";
    }
    @PostMapping("/sell")
    public String vendre(@RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        Integer userId = 1;
        try {
            fundTransactionService.sell(userId, amount);
            redirectAttributes.addFlashAttribute("message", "vente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur de vente.");
        }
        return "redirect:/fund-management";
    }
}
