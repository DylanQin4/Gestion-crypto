package mg.itu.cloud.wallet;

import mg.itu.cloud.user.User;
import mg.itu.cloud.user.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class VWalletService {
    private final VWalletRepository vWalletRepository;
    private final UserService userService;
    private final VUsersCryptoQuantityService vUsersCryptoQuantityService;

    public VWalletService(VWalletRepository vWalletRepository, UserService userService, VUsersCryptoQuantityService vUsersCryptoQuantityService) {
        this.vWalletRepository = vWalletRepository;
        this.userService = userService;
        this.vUsersCryptoQuantityService = vUsersCryptoQuantityService;
    }

    public Optional<VWallet> getVWalletByUserId(Integer userId) {
        return vWalletRepository.findById(userId);
    }

    public WalletResponse getWalleResponsetByUserId(Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return null;
        }
        Optional<VWallet> vWallet = vWalletRepository.findById(userId);
        return vWallet.map(wallet -> new WalletResponse(
                wallet.getTotalFundsAmount(),
                vUsersCryptoQuantityService.getAllUsersCrypto(userId)
        )).orElseGet(() -> new WalletResponse(BigDecimal.ZERO, new ArrayList<>()));
    }
}
