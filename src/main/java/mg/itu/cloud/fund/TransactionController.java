package mg.itu.cloud.fund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/fonds")
    public String afficherPageFonds(Model model) {
        Long walletId = 1L; // ID du portefeuille (à récupérer dynamiquement)

        BigDecimal solde = transactionService.getSolde(walletId);  // Corrected method name
        List<Transaction> transactions = transactionService.getTransactionHistory(walletId);

        model.addAttribute("solde", solde); // Corrected attribute name
        model.addAttribute("transactions", transactions);

        return "jsp/pages/fonds";
    }

    @PostMapping("/depot")
    public String deposer(@RequestParam Long walletId, @RequestParam BigDecimal montant, RedirectAttributes redirectAttributes) {
        try {
            transactionService.deposit(walletId, montant); // Corrected method name
            redirectAttributes.addFlashAttribute("message", "Dépôt effectué avec succès.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) { // Catch RuntimeException as well
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors du dépôt."); // More general error message
        }
        return "redirect:/fonds";
    }

    @PostMapping("/retrait")
    public String retirer(@RequestParam Long walletId, @RequestParam BigDecimal montant, RedirectAttributes redirectAttributes) {
        try {
            transactionService.withdraw(walletId, montant); // Corrected method name
            redirectAttributes.addFlashAttribute("message", "Retrait effectué avec succès.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) { // Catch RuntimeException
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors du retrait.");
        }
        return "redirect:/fonds";
    }
}