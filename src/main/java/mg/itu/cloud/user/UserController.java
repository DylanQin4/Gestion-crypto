package mg.itu.cloud.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update")
    public String updateUserProfile(@RequestParam Integer id, @RequestParam String name, Model model) {
        try {
            userService.updateName(id, name);
            model.addAttribute("successMessage", "Profile updated successfully!");
            model.addAttribute("user", userService.getUserById(id));
            return "pages/profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error updating profile.");
            return "redirect:/error";
        }
    }
    
    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Integer id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/error";
        }
        model.addAttribute("user", user);
        return "pages/profile";
    }
}
