package mg.itu.cloud.fund;

import jakarta.servlet.http.HttpSession;
import mg.itu.cloud.user.User;
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
    public String afficherPageFonds(Model model, @SessionAttribute(name = "user", required = false) User user) {
        if (user == null) {
            return "redirect:/auth/login";
        }
        Integer userId = user.getId();

        List<FundTransaction> transactions = fundTransactionService.getFundTransactionHistory(userId);

        model.addAttribute("transactions", transactions);
        return "pages/fund";
    }

    @PostMapping("/deposit")
    public String deposer(@RequestParam BigDecimal amount, RedirectAttributes redirectAttributes, @SessionAttribute(name = "user", required = false) User user) {
        if (user == null) {
            return "redirect:/auth/login";
        }
        Integer userId = user.getId();
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
    public String retirer(@RequestParam BigDecimal amount, RedirectAttributes redirectAttributes, @SessionAttribute(name = "user", required = false) User user) {
        if (user == null) {
            return "redirect:/auth/login";
        }
        Integer userId = user.getId();
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

    @GetMapping("/requests")
    public String getAllRequests(Model model, @SessionAttribute(name = "user", required = false) User user) {
        if (user == null || !user.isAdmin()) {
            return "redirect:/fund-management";
        }
        List<FundTransaction> transactionsPending = fundTransactionService.getAllRequests();
        model.addAttribute("transactions", transactionsPending);
        return "admin/validate-fund";
    }

    @GetMapping("/requests/validate/{id}")
    public String validateRequest(@PathVariable Integer id, RedirectAttributes redirectAttributes, @SessionAttribute(name = "user", required = false) User user) {
        if (user == null || !user.isAdmin()) {
            return "redirect:/fund-management";
        }
        try {
            fundTransactionService.validateRequest(id);
            redirectAttributes.addFlashAttribute("message", "Transaction validée avec succès.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors de la validation.");
        }
        return "redirect:/fund-management/requests";
    }

}