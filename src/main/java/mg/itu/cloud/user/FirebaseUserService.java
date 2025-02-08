package mg.itu.cloud.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FirebaseUserService {

    public List<UserRecord> getAllUsers() throws FirebaseAuthException {
        List<UserRecord> users = new ArrayList<>();
        ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
        while (page != null) {
            users.addAll((Collection<? extends UserRecord>) page.getValues());
            page = page.getNextPage();
        }
        return users;
    }
}

