@import mg.itu.cloud.crypto.Crypto
@import java.util.List
@import mg.itu.cloud.user.User

@param User user

@param List<Crypto> allCrypto

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
                    @for(Crypto crypto : allCrypto)
                        <tr>
                            <td class="hidden">${crypto.getId()}</td>
                            <td>${crypto.getSymbol()}</td>
                            <td>${crypto.getName()}</td>
                            <td id="price-${crypto.getId()}"></td>
                            <td id="change-${crypto.getId()}"></td>
                            <td>
                                <a class="btn-secondary" href="/crypto/evolution/${crypto.getId()}">Graphe</a>
                            </td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    </div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        function updateCryptoPrices() {
            const cryptoRows = document.querySelectorAll("tbody tr");

            cryptoRows.forEach(row => {
                const id = row.querySelector("td:first-child").textContent; // Récupère l'ID crypto

                fetch("/crypto/last-price/" + id)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Erreur lors de la récupération du prix");
                        }
                        return response.json();
                    })
                    .then(data => {
                        document.getElementById('price-' + id).textContent = parseFloat(data.price).toFixed(8) + " $";
                        document.getElementById('change-' + id).textContent = parseFloat(data.change).toFixed(8) + " %";
                    })
                    .catch(error => console.error("Erreur pour " + id + ": ", error));
            });
        }

        // Mise à jour initiale
        updateCryptoPrices();

        // Mise à jour toutes les 10 secondes
        setInterval(updateCryptoPrices, 10000);
    });
</script>


`, user = user)