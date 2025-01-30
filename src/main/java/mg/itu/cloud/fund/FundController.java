package mg.itu.cloud.fund;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("fund-management")
public class FundController {
    @GetMapping
    public String getFund(Model model) {
        return "pages/fund";
    }
}
