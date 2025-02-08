package mg.itu.cloud.user;

import mg.itu.cloud.crypto.transactions.CryptoTransaction;
import java.util.List;

public class UserTransactionsResponse {
    private String userName;
    private List<CryptoTransaction> buyTransactions;
    private List<CryptoTransaction> sellTransactions;

    // Constructeur
    public UserTransactionsResponse(String userName, List<CryptoTransaction> buyTransactions, List<CryptoTransaction> sellTransactions) {
        this.userName = userName;
        this.buyTransactions = buyTransactions;
        this.sellTransactions = sellTransactions;
    }

    // Getters et Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<CryptoTransaction> getBuyTransactions() {
        return buyTransactions;
    }

    public void setBuyTransactions(List<CryptoTransaction> buyTransactions) {
        this.buyTransactions = buyTransactions;
    }

    public List<CryptoTransaction> getSellTransactions() {
        return sellTransactions;
    }

    public void setSellTransactions(List<CryptoTransaction> sellTransactions) {
        this.sellTransactions = sellTransactions;
    }
}
