package mg.itu.cloud.fund;

import java.math.BigDecimal;


import mg.itu.cloud.fund.*;
import mg.itu.cloud.wallet.*;

import mg.itu.cloud.fund.TransactionRepository;
import mg.itu.cloud.wallet.WalletRepository;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private WalletRepository walletRepository;

    public Transaction deposit(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setFundBalance(wallet.getFundBalance().add(amount));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(wallet, TransactionType.DEPOSIT, TransactionStatus.PENDING, amount);
        return transactionRepository.save(transaction);
    }

    public Transaction buy(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getFundBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds for purchase");
        }

        wallet.setFundBalance(wallet.getFundBalance().subtract(amount));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction(wallet, TransactionType.BUY, TransactionStatus.PENDING, amount);
        return transactionRepository.save(transaction);
    }
}
