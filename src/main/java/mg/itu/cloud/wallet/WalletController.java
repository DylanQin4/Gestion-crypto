package mg.itu.cloud.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showWallet(Model model) {
        Integer userId = 1;
        model.addAttribute("wallet", vWalletService.getWalleResponsetByUserId(userId));
        return "pages/wallet";
    }
}
