package mg.itu.cloud.sync;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    public Firestore firestore;

    @Autowired
    private FirebaseApp firebaseApp;

    @PostConstruct
    public void initFirestore() {
        this.firestore = FirestoreClient.getFirestore(firebaseApp);
    }

    public void sendData(String collectionName, Map<String, Object> data) throws ApiException {
        DocumentReference docRef = firestore.collection(collectionName).document();
        WriteResult result;
        try {
            result = docRef.set(data).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Document written with ID: " + result.getUpdateTime());
    }
}