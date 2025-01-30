package mg.itu.cloud.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my-wallet")
public class WalletController {
    @GetMapping
    public String getWallet(Model model) {
//        model.addAttribute("")
        return "pages/wallet";
    }
}
