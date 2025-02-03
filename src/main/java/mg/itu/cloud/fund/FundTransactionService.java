package mg.itu.cloud.fund;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import mg.itu.cloud.user.User;
import mg.itu.cloud.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundTransactionService {
    private final FundTransactionRepository fundTransactionRepository;
    private final UserRepository userRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    @Autowired
    public FundTransactionService(FundTransactionRepository fundTransactionRepository, UserRepository userRepository, TransactionTypeRepository transactionTypeRepository) {
        this.fundTransactionRepository = fundTransactionRepository;
        this.userRepository = userRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public List<FundTransaction> getFundTransactionHistory(Integer userId) {
        return fundTransactionRepository.findByUserId(userId);
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
        FundTransaction transaction = new FundTransaction(user.get(), deposit.get(), amount);
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
        FundTransaction transaction = new FundTransaction(user.get(), withdrawal.get(), amount);
        fundTransactionRepository.save(transaction);
    }
}