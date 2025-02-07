package mg.itu.cloud.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.validation.Valid;
import mg.itu.cloud.crypto.transactions.CryptoTransactionService;

@RestController
@RequestMapping("/api/users")
public class UserController {
      
    private final UserService userService;
    private final CryptoTransactionService cryptoTransactionService;

    public UserController(CryptoTransactionService cryptoTransactionService, UserService userService) {
        this.cryptoTransactionService = cryptoTransactionService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return user;
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (userService.checkIfEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("L'email est déjà utilisé");
        }
        return userService.save(user);
    }

    @GetMapping("/check-email")
    public boolean checkEmailExists(@RequestParam String email) {
        return userService.checkIfEmailExists(email);
    }
    @PutMapping("/{id}/name")
    public User updateUserName(@PathVariable Integer id, @RequestParam String newName) {
        return userService.updateName(id, newName);
    }

    @GetMapping("/{id}/info")
    public User getUserInfo(@PathVariable Integer id) {
        return userService.getUserInfo(id);
    }

    @PutMapping("/{id}/role")
    public boolean changeUserRole(@PathVariable Integer id, @RequestParam String newRoleName) {
        return userService.changeUserRole(id, newRoleName);
    }

}
