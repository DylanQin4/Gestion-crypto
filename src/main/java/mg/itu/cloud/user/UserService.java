package mg.itu.cloud.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public User getUserById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public UserService(UserRepository Repository) {
        this.repository = Repository;
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

}
