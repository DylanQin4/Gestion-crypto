@import mg.itu.cloud.crypto.RealTimePrice
@import java.util.List

@param List<RealTimePrice> allCrypto

@template.layout.main(content = @`
    <div class="card w-full h-full">
        <div class="card-body">
            <div class="flex flex-col space-y-2">
                <h2 class="text-3xl font-bold mb-2">Prix des cryptomonnaies</h2>
                <p class="text-gray-500">Prix en temps réel des cryptomonnaies</p>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>Symbol</th>
                        <th>Nom</th>
                        <th>Prix</th>
                        <th>Change</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    @for(RealTimePrice crypto : allCrypto)
                        <tr>
                            <td>${crypto.cryptl().getSymbol()}</td>
                            <td>${crypto.cryptl().getName()}</td>
                            <td>${crypto.price()}</td>
                            <td>${crypto.change()}%</td>
                            <td>
                                <a class="btn-secondary" href="/crypto/evolution/${crypto.cryptl().getId()}">Graphe</a>
                            </td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    </div>
`)