package mg.itu.cloud.crypto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("crypto-prices")
public class CryptoPricesController {
    @GetMapping
    public String getRealTimePrices(Model model) {
        return "pages/crypto-prices";
    }
}
