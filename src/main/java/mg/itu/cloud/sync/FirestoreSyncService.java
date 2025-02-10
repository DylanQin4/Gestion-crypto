package mg.itu.cloud.sync;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import mg.itu.cloud.fund.*;
import mg.itu.cloud.user.Role;
import mg.itu.cloud.user.User;
import mg.itu.cloud.user.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class FirestoreSyncService {

    private final FirestoreService firestoreService;
    private final FundTransactionService fundTransactionService;
    private final TransactionTypeRepository transactionTypeRepository;
    private final UserService userService;

    public FirestoreSyncService(FirestoreService firestoreService, FundTransactionService fundTransactionService, TransactionTypeRepository transactionTypeRepository, UserService userService) {
        this.firestoreService = firestoreService;
        this.fundTransactionService = fundTransactionService;
        this.transactionTypeRepository = transactionTypeRepository;
        this.userService = userService;
    }

    @Scheduled(fixedRate = 15000)
    public void syncFirestoreToDatabase() {
        try {
            // 1. Récupérer les nouvelles transactions de Firestore
            QuerySnapshot querySnapshot = firestoreService.firestore.collection("fundTransactions").get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            // 2. Insérer les nouvelles transactions dans la base de données locale
            for (DocumentSnapshot document : documents) {
                if (!document.exists()) {
                    continue;
                }

                Map<String, Object> data = document.getData();
                Integer id = ((Long) data.get("id")).intValue();
                Integer userId = ((Long) data.get("userId")).intValue();
                Integer transactionTypeId = ((Long) data.get("transactionTypeId")).intValue();
                String status = (String) data.get("status");
                String amountStr = (String) data.get("amount");
                BigDecimal amount = new BigDecimal(amountStr);
                Object dateObj = data.get("transactionDate");
                Instant transactionDate;
                if (dateObj instanceof Long) {
                    transactionDate = Instant.ofEpochMilli((Long) dateObj);
                } else if (dateObj instanceof String) {
                    String dateStr = (String) dateObj;
                    try {
                        // On essaie de parser avec Instant.parse si la chaîne contient un indicateur de fuseau horaire (ex: "Z")
                        transactionDate = Instant.parse(dateStr);
                    } catch (DateTimeParseException e) {
                        // Sinon, on considère que la date est au format ISO_LOCAL_DATE_TIME (sans fuseau) et on le convertit en UTC
                        transactionDate = LocalDateTime.parse(dateStr).atZone(ZoneOffset.UTC).toInstant();
                    }
                } else {
                    throw new IllegalArgumentException("Type de transactionDate non supporté : " + (dateObj != null ? dateObj.getClass() : "null"));
                }

                // Vérifier si la transaction existe déjà dans la base de données locale
                FundTransaction existingTransaction = fundTransactionService.getById(id);
                if (existingTransaction == null) {
                    // Créer une nouvelle transaction dans la base locale
                    TransactionType transactionType = transactionTypeRepository.findById(transactionTypeId)
                            .orElseThrow(() -> new IllegalArgumentException("Type de transaction inconnu: " + transactionTypeId));

                    FundTransaction newTransaction = new FundTransaction(null, userId, transactionType, status, amount, transactionDate);
                    fundTransactionService.createFundTransaction(newTransaction);

                    // Supprimer le document de Firestore pour "nettoyer" l'enregistrement
                    firestoreService.firestore
                            .collection("fundTransactions")
                            .document(document.getId())
                            .delete()
                            .get();
                }
            }

            // 3. Pousser les transactions validées vers Firestore
            List<FundTransaction> transactions = fundTransactionService.getAllFundTransactions();
            for (FundTransaction transaction : transactions) {
                // Vérifier si la transaction existe déjà dans Firestore
                QuerySnapshot qSnapshot = firestoreService.firestore
                        .collection("fundTransactions")
                        .whereEqualTo("id", transaction.getId())
                        .get()
                        .get();
                if (qSnapshot.isEmpty()) {
                    try {
                        // Pousser la transaction vers Firestore
                        Map<String, Object> data = fundTransactionService.convertFundTransactionToMap(transaction);
                        firestoreService.sendData("fundTransactions", data);
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ApiException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 60000)
    private void syncUser() throws ExecutionException, InterruptedException {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            // Vérifier si l'utilisateur existe déjà dans Firestore
            QuerySnapshot qSnapshot = firestoreService.firestore
                    .collection("users")
                    .whereEqualTo("id", user.getId())
                    .get()
                    .get();
            if (qSnapshot.isEmpty()) {
                try {
                    // Pousser l'utilisateur vers Firestore
                    Map<String, Object> data = userService.convertUserToMap(user);
                    firestoreService.sendData("users", data);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}