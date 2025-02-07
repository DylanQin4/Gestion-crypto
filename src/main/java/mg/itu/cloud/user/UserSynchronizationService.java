package mg.itu.cloud.user;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserSynchronizationService {

    private final FirebaseUserService firebaseUserService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserSynchronizationService(FirebaseUserService firebaseUserService, RoleService roleService, UserRepository userRepository) {
        this.firebaseUserService = firebaseUserService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    public void synchronizeUsers() throws FirebaseAuthException {
        List<UserRecord> firebaseUsers = firebaseUserService.getAllUsers();
        for (UserRecord firebaseUser : firebaseUsers) {
            String displayName = firebaseUser.getDisplayName();
            String lastName = null;

            if (displayName != null && !displayName.isEmpty()) {
                String[] nameParts = displayName.split("[,\\s]+");

                // firstname,lastname
                if (nameParts.length > 1) {
                    lastName = nameParts[nameParts.length - 1];
                }
            }
            if (lastName == null) {
                String email = firebaseUser.getEmail();
                if (email != null && !email.isEmpty()) {
                    String[] emailParts = email.split("@");
                    if (emailParts.length > 0) {
                        lastName = emailParts[0];
                    }
                }
            }

            // Vérifiez si l'email existe deja dans la base
            if (!userRepository.existsByEmail(firebaseUser.getEmail())) {
                User user = new User(
                        lastName,
                        firebaseUser.getEmail(),
                        new HashSet<>(List.of(roleService.getRoleByName(Roles.USER.name())))
                );
                userRepository.save(user);
            }
        }
    }
}

