package mg.itu.cloud.fund;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionTypeService {
    private final TransactionTypeRepository repository;

    public TransactionTypeService(TransactionTypeRepository repository) {
        this.repository = repository;
    }

    public List<TransactionType> getAllTransactionTypes() {
        return repository.findAll();
    }
}
