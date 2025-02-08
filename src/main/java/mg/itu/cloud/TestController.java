package mg.itu.cloud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class TestController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("username", "John Doe");
        return "redirect:/my-wallet";
    }
}
