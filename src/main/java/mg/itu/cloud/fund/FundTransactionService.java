package mg.itu.cloud.fund;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mg.itu.cloud.sync.FirestoreService;
import mg.itu.cloud.user.User;
import mg.itu.cloud.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FundTransactionService {
    private final FundTransactionRepository fundTransactionRepository;
    private final UserRepository userRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final FirestoreService firestoreService;

    public FundTransactionService(FundTransactionRepository fundTransactionRepository, UserRepository userRepository, TransactionTypeRepository transactionTypeRepository, FirestoreService firestoreService) {
        this.fundTransactionRepository = fundTransactionRepository;
        this.userRepository = userRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.firestoreService = firestoreService;
    }

    public List<FundTransaction> getFundTransactionHistory(Integer userId) {
        return fundTransactionRepository.findByUserId(userId);
    }

    public List<FundTransaction> getAllFundTransactions() {
        return fundTransactionRepository.findAll();
    }

    public void deposit(Integer userId, BigDecimal amount) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouve");
        }
        Optional<TransactionType> deposit = transactionTypeRepository.findByName(Type.DEPOSIT.name());
        if (deposit.isEmpty()) {
            throw new RuntimeException("Type de transaction 'DEPOSIT' non trouve");
        }
        FundTransaction transaction = new FundTransaction(userId, deposit.get(), Status.PENDING.name(), amount, Instant.now());
        fundTransactionRepository.save(transaction);
    }

    public void withdraw(Integer userId, BigDecimal amount) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouve");
        }
        Optional<TransactionType> withdrawal = transactionTypeRepository.findByName(Type.WITHDRAWAL.name());
        if (withdrawal.isEmpty()) {
            throw new RuntimeException("Type de transaction 'WITHDRAWAL' non trouve");
        }
        FundTransaction transaction = new FundTransaction(userId, withdrawal.get(), Status.PENDING.name(), amount, Instant.now());
        fundTransactionRepository.save(transaction);
    }

    public void createFundTransaction(FundTransaction transaction) {
        fundTransactionRepository.save(transaction);
    }

    public List<FundTransaction> getAllRequests() {
        return fundTransactionRepository.findByStatus(Status.PENDING.name());
    }

    public void validateRequest(Integer id) {
        Optional<FundTransaction> transaction = fundTransactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction non trouvee");
        }
        if (transaction.get().getStatus().equals(Status.VALIDATE.name())) {
            throw new IllegalArgumentException("Transaction deja validee");
        }
        if (transaction.get().getStatus().equals(Status.DELETED.name())) {
            throw new IllegalArgumentException("Transaction invalide");
        }
        transaction.get().setStatus(Status.VALIDATE.name());
        fundTransactionRepository.save(transaction.get());
        // Firestore
        try {
            firestoreService.updateFundTransactionStatus("fundTransactions", id, Status.VALIDATE);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void invalidateRequest(Integer id) {
        Optional<FundTransaction> transaction = fundTransactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction non trouvee");
        }
        if (transaction.get().getStatus().equals(Status.VALIDATE.name())) {
            throw new IllegalArgumentException("Transaction deja validee");
        }
        transaction.get().setStatus(Status.DELETED.name());
        fundTransactionRepository.save(transaction.get());
        // Firestore
        try {
            firestoreService.updateFundTransactionStatus("fundTransactions", id, Status.DELETED);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> convertFundTransactionToMapWithJackson(FundTransaction fundTransaction) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(fundTransaction, new TypeReference<Map<String, Object>>() {});
    }

    public Map<String, Object> convertFundTransactionToMap(FundTransaction fundTransaction) {
        return Map.of(
                "id", fundTransaction.getId(),
                "userId", fundTransaction.getUserId(),
                "transactionTypeId", fundTransaction.getTransactionType().getId(),
                "status", fundTransaction.getStatus(),
                "amount", fundTransaction.getAmount(),
                "transactionDate", fundTransaction.getTransactionDate().toString()
        );
    }

    public FundTransaction getById(Integer id) {
        Optional<FundTransaction> transaction = fundTransactionRepository.findById(id);
        return transaction.orElse(null);
    }

    public void createFundTransaction(Map<String, Object> data) {
        ObjectMapper objectMapper = new ObjectMapper();
        FundTransaction fundTransaction = objectMapper.convertValue(data, FundTransaction.class);
        fundTransactionRepository.save(fundTransaction);
    }

    public List<FundTransaction> getFundTransactionByStatus(String status) {
        return fundTransactionRepository.findByStatus(status);
    }
}