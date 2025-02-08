package mg.itu.cloud.sync;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import mg.itu.cloud.fund.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class FirestoreSyncService {

    private final FirestoreService firestoreService;
    private final FundTransactionService fundTransactionService;
    private final TransactionTypeRepository transactionTypeRepository;

    public FirestoreSyncService(FirestoreService firestoreService, FundTransactionService fundTransactionService, TransactionTypeRepository transactionTypeRepository) {
        this.firestoreService = firestoreService;
        this.fundTransactionService = fundTransactionService;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Scheduled(fixedRate = 15000)
    public void syncFirestoreToDatabase() {
        try {
            // 1. Recuperer les nouvelles transactions de Firestore
            QuerySnapshot querySnapshot = firestoreService.firestore.collection("fundTransactions").get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            // 2. Inserer les nouvelles transactions dans la base de donnees locale
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
                        // On essaie d'abord de parser avec Instant.parse si la chaîne contient un indicateur de fuseau horaire (ex: "Z")
                        transactionDate = Instant.parse(dateStr);
                    } catch (DateTimeParseException e) {
                        // Sinon, on considere que la date est au format ISO_LOCAL_DATE_TIME (sans fuseau) et on le convertit en UTC
                        transactionDate = LocalDateTime.parse(dateStr).atZone(ZoneOffset.UTC).toInstant();
                    }
                } else {
                    throw new IllegalArgumentException("Type de transactionDate non supporte : " + (dateObj != null ? dateObj.getClass() : "null"));
                }

                // Verifier si la transaction existe deja dans la base de donnees locale
                FundTransaction existingTransaction = fundTransactionService.getById(id);
                if (existingTransaction == null) {
                    // Creer une nouvelle transaction
                    TransactionType transactionType = transactionTypeRepository.findById(transactionTypeId)
                            .orElseThrow(() -> new IllegalArgumentException("Type de transaction inconnu: " + transactionTypeId));

                    FundTransaction newTransaction = new FundTransaction(id, userId, transactionType, status, amount, transactionDate);
                    fundTransactionService.createFundTransaction(newTransaction);
                }
            }

            // 3. Pousser les transactions validees vers Firestore
            List<FundTransaction> transactions = fundTransactionService.getAllFundTransactions();
            for (FundTransaction transaction : transactions) {
                // Verifier si la transaction existe deja dans Firestore
                QuerySnapshot qSnapshot = firestoreService.firestore.collection("fundTransactions").whereEqualTo("id", transaction.getId()).get().get();
                if (qSnapshot.isEmpty()) {
                    try {
                        // Pousser la transaction vers Firestore
                        Map<String, Object> data = fundTransactionService.convertFundTransactionToMap(transaction);
                        firestoreService.sendData("fundTransactions", data);
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("FundTransactionId '" + transaction.getId() + "' existe deja dans Firestore.");
                }
            }
        } catch (ApiException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}