package mg.itu.cloud.user;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructeur pour injecter le service
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint pour récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return user;
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
    }

    // Endpoint pour enregistrer un utilisateur
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        // Vous pouvez ajouter une vérification supplémentaire pour voir si l'email existe déjà
        if (userService.checkIfEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("L'email est déjà utilisé");
        }
        return userService.save(user);
    }

    // Endpoint pour vérifier si l'email existe déjà
    @GetMapping("/check-email")
    public boolean checkEmailExists(@RequestParam String email) {
        return userService.checkIfEmailExists(email);
    }

    // Endpoint pour mettre à jour le nom d'un utilisateur
    @PutMapping("/{id}/name")
    public User updateUserName(@PathVariable Integer id, @RequestParam String newName) {
        return userService.updateName(id, newName);
    }

    // Endpoint pour obtenir les informations d'un utilisateur
    @GetMapping("/{id}/info")
    public User getUserInfo(@PathVariable Integer id) {
        return userService.getUserInfo(id);
    }

    // Endpoint pour changer le rôle d'un utilisateur
    @PutMapping("/{id}/role")
    public boolean changeUserRole(@PathVariable Integer id, @RequestParam String newRoleName) {
        return userService.changeUserRole(id, newRoleName);
    }
}
