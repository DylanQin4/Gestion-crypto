package mg.itu.cloud.fund;

import gg.jte.springframework.boot.autoconfigure.JteViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        Long walletId = 1L;

        BigDecimal solde = transactionService.getSolde(walletId);
        List<Transaction> transactions = transactionService.getTransactionHistory(walletId);

        model.addAttribute("solde", solde);
        model.addAttribute("transactions", transactions);

        return "fonds"; 
    }

    @PostMapping("/depot")
    public String deposer(@RequestParam Long walletId, @RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        try {
            transactionService.deposit(walletId, amount);
            redirectAttributes.addFlashAttribute("message", "Dépôt effectué avec succès.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors du dépôt.");
        }
        return "redirect:/fonds"; 
    }

    @PostMapping("/retrait")
    public String retirer(@RequestParam Long walletId, @RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        try {
            transactionService.withdraw(walletId, amount);
            redirectAttributes.addFlashAttribute("message", "Retrait effectué avec succès.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors du retrait.");
        }
        return "redirect:/fonds"; 
    }
}