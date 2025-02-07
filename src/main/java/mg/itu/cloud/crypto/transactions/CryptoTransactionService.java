package mg.itu.cloud.crypto.transactions;

import mg.itu.cloud.crypto.PriceHistory;
import mg.itu.cloud.crypto.PriceHistoryService;
import mg.itu.cloud.fund.*;
import mg.itu.cloud.user.UserService;
import mg.itu.cloud.wallet.VUsersCryptoQuantityService;
import mg.itu.cloud.wallet.VWallet;
import mg.itu.cloud.wallet.VWalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class CryptoTransactionService {
    private final CryptoTransactionRepository cryptoTransactionRepository;
    private final VWalletService vWalletService;
    private final PriceHistoryService priceHistoryService;
    private final TransactionTypeRepository transactionTypeRepository;
    private final UserService userService;
    private final FundTransactionService fundTransactionService;
    private final VUsersCryptoQuantityService vUsersCryptoQuantityService;

    public CryptoTransactionService(
            CryptoTransactionRepository cryptoTransactionRepository,
            VWalletService vWalletService,
            PriceHistoryService priceHistoryService,
            TransactionTypeRepository transactionTypeRepository,
            UserService userService,
            FundTransactionService fundTransactionService,
            VUsersCryptoQuantityService vUsersCryptoQuantityService
    ) {
        this.cryptoTransactionRepository = cryptoTransactionRepository;
        this.vWalletService = vWalletService;
        this.priceHistoryService = priceHistoryService;
        this.transactionTypeRepository = transactionTypeRepository;
        this.userService = userService;
        this.fundTransactionService = fundTransactionService;
        this.vUsersCryptoQuantityService = vUsersCryptoQuantityService;
    }

    @Transactional
    public void buy(Integer userId, Integer cryptoId, BigDecimal quantity) throws Exception {
        Optional<VWallet> wallet = vWalletService.getVWalletByUserId(userId);
        if (wallet.isEmpty()){
            throw new IllegalArgumentException("Portefeuille non trouve");
        }

        PriceHistory lastPrice = priceHistoryService.getLastPriceBy(cryptoId);
        Optional<TransactionType> typeBuy = transactionTypeRepository.findByName(Type.BUY.name());
        Optional<TransactionType> typeWithdrawal = transactionTypeRepository.findByName(Type.WITHDRAWAL.name());
        Instant instant = Instant.now();

        if (typeBuy.isEmpty()) {
            throw new IllegalArgumentException("Type de transaction BUY non trouve");
        }
        if (typeWithdrawal.isEmpty()) {
            throw new IllegalArgumentException("Type de transaction WITHDRAWAL non trouve");
        }

        if (userService.getUserById(userId) == null) {
            throw new IllegalArgumentException("Utilisateur non trouve");
        }

        BigDecimal amount = lastPrice.getClose().multiply(quantity);

        if (wallet.get().getTotalFundsAmount().compareTo(amount) >= 0) {
            CryptoTransaction newCryptoTransaction = new CryptoTransaction(
                    null,
                    userId,
                    typeBuy.get(),
                    cryptoId,
                    quantity,
                    lastPrice.getClose(),
                    amount,
                    instant
            );
            FundTransaction newFundTransaction = new FundTransaction(
                    userId,
                    typeWithdrawal.get(),
                    Status.VALIDATE.name(),
                    amount,
                    instant
            );
            // persistance des transactions
            this.createCryptoTransaction(newCryptoTransaction);
            // apres l'achat, on retire le montant de la transaction du portefeuille
            fundTransactionService.createFundTransaction(newFundTransaction);
        } else {
            throw new Exception("Votre fond est insuffisant pour cet achat");
        }
    }

    @Transactional
    public void sell(Integer userId, Integer cryptoId, BigDecimal quantity) throws Exception {
        BigDecimal userCryptoQuantity = vUsersCryptoQuantityService.getTotalQuantityByUserIdAndCryptoId(userId, cryptoId);

        PriceHistory lastPrice = priceHistoryService.getLastPriceBy(cryptoId);
        Optional<TransactionType> typeSell = transactionTypeRepository.findByName(Type.SELL.name());
        Optional<TransactionType> typeDeposit = transactionTypeRepository.findByName(Type.DEPOSIT.name());
        Instant instant = Instant.now();

        if (typeSell.isEmpty()) {
            throw new IllegalArgumentException("Type de transaction BUY non trouve");
        }
        if (typeDeposit.isEmpty()) {
            throw new IllegalArgumentException("Type de transaction WITHDRAWAL non trouve");
        }
        if (userService.getUserById(userId) == null) {
            throw new IllegalArgumentException("Utilisateur non trouve");
        }

        BigDecimal amount = lastPrice.getClose().multiply(quantity);

        if (userCryptoQuantity.compareTo(quantity) >= 0) {
            CryptoTransaction newCryptoTransaction = new CryptoTransaction(
                    null,
                    userId,
                    typeSell.get(),
                    cryptoId,
                    quantity,
                    lastPrice.getClose(),
                    amount,
                    instant
            );
            FundTransaction newFundTransaction = new FundTransaction(
                    userId,
                    typeDeposit.get(),
                    Status.VALIDATE.name(),
                    amount,
                    instant
            );
            // persistance des transactions
            this.createCryptoTransaction(newCryptoTransaction);
            // apres la vente, on depose le montant de la transaction dans le portefeuille
            fundTransactionService.createFundTransaction(newFundTransaction);
        } else {
            throw new Exception("La quantite de votre crypto est insuffisante pour cette vente");
        }
    }


    public void createCryptoTransaction(CryptoTransaction cryptoTransaction) {
        cryptoTransactionRepository.save(cryptoTransaction);
    }

 
 
}