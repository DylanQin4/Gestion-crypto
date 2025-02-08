package mg.itu.cloud.sync;

import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.annotation.PostConstruct;
import mg.itu.cloud.fund.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public void updateFundTransactionStatus(String collectionName, Integer fundTransactionId, Status newStatus)
            throws ApiException, ExecutionException, InterruptedException {
        // 1. Get fundTransaction
        QuerySnapshot querySnapshot = firestore.collection(collectionName).whereEqualTo("id", fundTransactionId).get().get();

        // 2. Check if the document exists
        if (querySnapshot.isEmpty()) {
            throw new IllegalArgumentException("Transaction with ID " + fundTransactionId + " not found.");
        }

        // 3. Get the first
        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);

        // 4. Update the document
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", newStatus.name());
        ApiFuture<WriteResult> writeResultFuture = documentSnapshot.getReference().update(updates);
        WriteResult result = writeResultFuture.get();
        System.out.println("Document updated with ID: " + fundTransactionId + " and status: " + newStatus.name());
    }
}