package mg.itu.cloud.wallet;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VWalletService {
    private final VWalletRepository vWalletRepository;

    public VWalletService(VWalletRepository vWalletRepository) {
        this.vWalletRepository = vWalletRepository;
    }

    public Optional<VWallet> getVWalletByUserId(Integer userId) {
        return vWalletRepository.findById(userId);
    }
}
