package mg.itu.cloud.sync;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.firestore.QuerySnapshot;
import mg.itu.cloud.crypto.Crypto;
import mg.itu.cloud.crypto.CryptoService;
import mg.itu.cloud.fund.TransactionType;
import mg.itu.cloud.fund.TransactionTypeService;
import mg.itu.cloud.user.Role;
import mg.itu.cloud.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class DataInitializer {

    private final FirestoreService firestoreService;
    private final RoleService roleService;
    private final CryptoService cryptoService;
    private final TransactionTypeService transactionTypeService;

    @Autowired
    public DataInitializer(FirestoreService firestoreService, RoleService roleService, CryptoService cryptoService, TransactionTypeService transactionTypeService) {
        this.firestoreService = firestoreService;
        this.roleService = roleService;
        this.cryptoService = cryptoService;
        this.transactionTypeService = transactionTypeService;
    }

    @PostConstruct
    public void initData() throws ExecutionException, InterruptedException {
        initializeData("roles", roleService.getAllRoles());
        initializeData("cryptos", cryptoService.getAllCrypto());
        initializeData("transactionTypes", transactionTypeService.getAllTransactionTypes());
    }

    private void initializeData(String collectionName, List<?> data) throws ExecutionException, InterruptedException {
        for (Object item : data) {
            if (item instanceof Role) {
                Role role = (Role) item;
                initializeRole(collectionName, role);
            } else if (item instanceof Crypto) {
                Crypto crypto = (Crypto) item;
                initializeCrypto(collectionName, crypto);
            } else if (item instanceof TransactionType) {
                TransactionType transactionType = (TransactionType) item;
                initializeTransactionType(collectionName, transactionType);
            }
        }
    }

    private void initializeRole(String collectionName, Role role) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = firestoreService.firestore.collection(collectionName)
                .whereEqualTo("name", role.getName())
                .get()
                .get();

        if (querySnapshot.isEmpty()) {
            Map<String, Object> mapRole = new HashMap<>();
            mapRole.put("id", role.getId());
            mapRole.put("name", role.getName());

            try {
                firestoreService.sendData(collectionName, mapRole);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Role '" + role.getName() + "' already exists in Firestore.");
        }
    }

    private void initializeCrypto(String collectionName, Crypto crypto) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = firestoreService.firestore.collection(collectionName)
                .whereEqualTo("name", crypto.getName())
                .get()
                .get();

        if (querySnapshot.isEmpty()) {
            // Crypto doesn't exist, send it to Firestore
            Map<String, Object> mapCrypto = new HashMap<>();
            mapCrypto.put("id", crypto.getId());
            mapCrypto.put("name", crypto.getName());
            mapCrypto.put("symbol", crypto.getSymbol());
            mapCrypto.put("imageUrl", crypto.getImageUrl());

            try {
                firestoreService.sendData(collectionName, mapCrypto);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Crypto '" + crypto.getName() + "' already exists in Firestore.");
        }
    }

    private void initializeTransactionType(String collectionName, TransactionType transactionType) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = firestoreService.firestore.collection(collectionName)
                .whereEqualTo("name", transactionType.getName())
                .get()
                .get();

        if (querySnapshot.isEmpty()) {
            Map<String, Object> mapTransactionType = new HashMap<>();
            mapTransactionType.put("id", transactionType.getId());
            mapTransactionType.put("name", transactionType.getName());

            try {
                firestoreService.sendData(collectionName, mapTransactionType);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Transaction type '" + transactionType.getName() + "' already exists in Firestore.");
        }
    }
}
