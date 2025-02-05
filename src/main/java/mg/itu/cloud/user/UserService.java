package mg.itu.cloud.user;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
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
        Optional<User> optionalUser = repository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(newName);
            return repository.save(user);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
    }

    public User getUserInfo(Integer userId) {
        Optional<User> optionalUser = repository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
    }

    @Transactional
    public boolean changeUserRole(Integer userId, String newRoleName) {
        Optional<User> optionalUser = repository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Récupérer l'ancien rôle et le supprimer
            user.getRoles().clear(); // Suppression de l'ancien rôle (il ne peut y en avoir qu'un seul)

            // Récupérer le nouveau rôle
            Optional<Role> optionalRole = roleRepository.findByName(newRoleName);
            if (optionalRole.isPresent()) {
                Role newRole = optionalRole.get();
                user.getRoles().add(newRole); // Ajouter le nouveau rôle
                repository.save(user); // Sauvegarder l'utilisateur avec son nouveau rôle
                return true;
            } else {
                throw new IllegalArgumentException("Rôle non trouvé");
            }
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
    }
}
