package mg.itu.cloud.user;

import com.google.firebase.auth.FirebaseAuthException;
import mg.itu.cloud.Config.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class UserSynchronizationScheduler {
    private final UserSynchronizationService userSynchronizationService;

    public UserSynchronizationScheduler(UserSynchronizationService userSynchronizationService) {
        this.userSynchronizationService = userSynchronizationService;
    }

    @Scheduled(fixedRate = AppConstants.SYNC_INTERVAL)
    public void synchronize() throws FirebaseAuthException {
        userSynchronizationService.synchronizeUsers();
    }
}

