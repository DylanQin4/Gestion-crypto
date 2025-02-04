package mg.itu.cloud.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my-wallet")
public class WalletController {
    @GetMapping
    public String showWallet() {
        return "pages/wallet";
    }
}
