package mg.itu.cloud.Auth;

import jakarta.servlet.http.HttpSession;
import mg.itu.cloud.user.User;
import mg.itu.cloud.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Vous n'êtes pas encore inscrit.");
            return "redirect:/auth/login?error=not_registered";
        }

        String authResponse = authenticationService.authenticate(email, password);
        if (authResponse.contains("Erreur")) {
            redirectAttributes.addFlashAttribute("error", authResponse);
            return "redirect:/auth/login?error=auth_failed";
        }

        session.setAttribute("user", user);
        return "redirect:/my-wallet";
    }


    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String name, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        String registerResponse = authenticationService.register(email, name, password);
        if (registerResponse.contains("Erreur")) {
            redirectAttributes.addFlashAttribute("error", registerResponse);
            return "redirect:/auth/register";
        }
        System.out.println("Register response: " + registerResponse);

        return "redirect:/auth/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "auth/registration-success";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }

    @GetMapping("/validate-email/{token}")
    public String validateEmail(@PathVariable String token) {
        return authenticationService.validateEmail(token);
    }
}
