package mg.itu.cloud.Config;

import jakarta.servlet.http.HttpSession;
import mg.itu.cloud.user.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("user")
    public User addUserToModel(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}
