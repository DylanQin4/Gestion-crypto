package mg.itu.cloud.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
}
