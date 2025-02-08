package mg.itu.cloud.wallet;

import jakarta.servlet.http.HttpSession;
import mg.itu.cloud.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/my-wallet")
public class WalletController {
    private final VUsersCryptoQuantityService vUsersCryptoQuantityService;
    private final VWalletService vWalletService;

    public WalletController(VUsersCryptoQuantityService vUsersCryptoQuantityService, VWalletService vWalletService) {
        this.vUsersCryptoQuantityService = vUsersCryptoQuantityService;
        this.vWalletService = vWalletService;
    }

    @GetMapping
    public String showWallet(Model model, @SessionAttribute(name = "user", required = false) User user) {
        if (user == null) {
            return "redirect:/auth/login";
        }
        Integer userId = user.getId();
        model.addAttribute("wallet", vWalletService.getWalleResponsetByUserId(userId));
        return "pages/wallet";
    }
}
