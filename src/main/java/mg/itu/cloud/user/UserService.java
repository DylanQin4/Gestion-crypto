package mg.itu.cloud.user;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public User getUserById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public User save(User user) {
        if (user.getId() != null) {
            Optional<User> existingUser = repository.findById(user.getId());
            if (existingUser.isPresent()) {
                if (!existingUser.get().getEmail().equals(user.getEmail())) {
                    throw new UnsupportedOperationException("La modification de l'email n'est pas autorisée.");
                }
            }
        }
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

        Optional<Role> optionalRole = roleRepository.findByName(newRoleName);
        if (!optionalRole.isPresent()) {
            throw new IllegalArgumentException("Rôle non trouvé");
        }

        Role newRole = optionalRole.get();
        user.getRoles().clear(); 
        user.getRoles().add(newRole);  // Ajouter le nouveau rôle
        repository.save(user); // Sauvegarder les modifications
        return true;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
