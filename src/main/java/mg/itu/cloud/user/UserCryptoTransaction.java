package mg.itu.cloud.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_crypto_transactions")
public class UserCryptoTransaction {

    @Id
    private Integer userId;  

    private String userName;  
    private String userEmail;  
    private String cryptocurrencyName;
    private String cryptocurrencySymbol;
    private Integer transactionTypeId;
    private String transactionType;
    private Double quantity;
    private Double priceUnit; 
    private Double totalAmount;
    private String transactionDate; 

    // Getters et Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCryptocurrencyName() {
        return cryptocurrencyName;
    }

    public void setCryptocurrencyName(String cryptocurrencyName) {
        this.cryptocurrencyName = cryptocurrencyName;
    }

    public String getCryptocurrencySymbol() {
        return cryptocurrencySymbol;
    }

    public void setCryptocurrencySymbol(String cryptocurrencySymbol) {
        this.cryptocurrencySymbol = cryptocurrencySymbol;
    }

    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Integer transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
