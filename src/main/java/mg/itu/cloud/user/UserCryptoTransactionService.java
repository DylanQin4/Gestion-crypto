package mg.itu.cloud.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserCryptoTransactionService {

    @Autowired
    private UserCryptoTransactionRepository repository;

    // Méthode pour récupérer les transactions de type "BUY" ou "SELL"
    public List<UserCryptoTransaction> getBuyAndSellTransactions() {
        List<String> transactionTypes = Arrays.asList("BUY", "SELL");
        return repository.findByTransactionTypeIn(transactionTypes);
    }
    
    public List<UserCryptoTransaction> getTransactionsByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}
