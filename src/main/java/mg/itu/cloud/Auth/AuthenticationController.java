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

        AuthenticatorResponse authResponse = authenticationService.authenticate(email, password);
        if (authResponse.status().equals("error")) {
            redirectAttributes.addFlashAttribute("error", authResponse.message());
            return "redirect:/auth/login?error=auth_failed";
        }

        session.setAttribute("idUser", user.getId());

        return "redirect:/auth/validate-pin";
    }

    @GetMapping("/validate-pin")
    public String validatePin() {
        return "auth/validation-pin";
    }

    @PostMapping("/validate-pin")
    public String validatePin(@RequestParam String pin, HttpSession session, RedirectAttributes redirectAttributes) {
        String sanitizedPin = pin.replaceAll(",", ""); // Supprimer les virgules
        System.out.println("PIN: " + sanitizedPin);
        AuthenticatorResponse response = authenticationService.validatePin(sanitizedPin);
        if (response.status().equals("error")) {
            redirectAttributes.addFlashAttribute("error", response.message());
            return "redirect:/auth/validate-pin";
        }

        try {
            User user = userService.getUserById((Integer) session.getAttribute("idUser"));
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "Utilisateur introuvable.");
            }
            session.setAttribute("user", user);
            session.setAttribute("access-token", response.accessToken());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
            return "redirect:/auth/validate-pin";
        }
        return "redirect:/my-wallet";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String name, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        AuthenticatorResponse registerResponse = authenticationService.register(email, name, password);
        if (registerResponse.status().equals("error")) {
            redirectAttributes.addFlashAttribute("error", registerResponse.message());
            return "redirect:/auth/register";
        }

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
        return authenticationService.validateEmail(token).message();
    }
}
