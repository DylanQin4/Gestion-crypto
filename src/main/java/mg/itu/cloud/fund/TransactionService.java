package mg.itu.cloud.fund;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.itu.cloud.wallet.Wallet;
import mg.itu.cloud.wallet.WalletRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    public BigDecimal getSolde(Long walletId) {  
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return wallet.getFundBalance(); 
    }

    public List<Transaction> getTransactionHistory(Long walletId) {
        return transactionRepository.findByWalletId(walletId);
    }

    public Transaction deposit(Long walletId, BigDecimal amount) { 
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setFundBalance(wallet.getFundBalance().add(amount));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(wallet, TransactionType.DEPOSIT, TransactionStatus.PENDING, amount);
        return transactionRepository.save(transaction);
    }

    public Transaction withdraw(Long walletId, BigDecimal amount) { 
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getFundBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds"); 
        }

        wallet.setFundBalance(wallet.getFundBalance().subtract(amount));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(wallet, TransactionType.WITHDRAWAL, TransactionStatus.PENDING, amount);
        return transactionRepository.save(transaction);
    }
}