package mg.itu.cloud.Auth;

import mg.itu.cloud.user.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UtilisateurService utilisateurService;

    public AuthenticationController(AuthenticationService authenticationService, UtilisateurService utilisateurService) {
        this.authenticationService = authenticationService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        if (!utilisateurService.checkIfEmailExists(loginRequest.getEmail())) {
            return "Vous n'etes pas inscrit";
        }
        return authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest.getEmail(), registerRequest.getNom(), registerRequest.getPassword());
    }

    @GetMapping("/validate-email/{token}")
    public String validateEmail(@PathVariable String token) {
        return authenticationService.validateEmail(token);
    }
}
