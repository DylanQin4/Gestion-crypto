package mg.itu.cloud.Controller;

import mg.itu.cloud.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        //utiliser setters et getters
        return authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
