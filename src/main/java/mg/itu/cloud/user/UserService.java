package mg.itu.cloud.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository Repository, RoleRepository roleRepository) {
        this.repository = Repository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public boolean checkIfEmailExists(String email) {
        return repository.existsByEmail(email);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    @Transactional
    public User updateName(Integer userId, String newName) {
        return repository.findById(userId)
                .map(user -> {
                    user.setName(newName);
                    return repository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }

    public User getUserInfo(Integer userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }

    @Transactional
    public boolean changeUserRole(Integer userId, String newRoleName) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        Role optionalRole = roleRepository.findByName(newRoleName);
        if (optionalRole == null) {
            throw new IllegalArgumentException("Rôle non trouvé");
        }

        user.getRoles().clear();
        user.getRoles().add(optionalRole);
        repository.save(user);
        return true;
    }

    public Map<String, Object> convertUserToMap(User user) {
        return Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "roles", user.getRoles().stream().map(Role::getName).toList()
        );
    }
}
