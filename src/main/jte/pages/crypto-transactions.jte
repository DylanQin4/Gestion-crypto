@import mg.itu.cloud.crypto.Crypto
@import java.util.List
@import mg.itu.cloud.user.User

@param User user

@param List<Crypto> allCrypto
@param String message
@param String error

@template.layout.main(content = @`
    <div class="card w-full h-full">
        <div class="card-body flex flex-col space-y-2 items-center">
            <form id="cryptoTransactionForm" class="w-96 peer grid gap-y-4" method="POST" action="/crypto/transactions">
                <input type="hidden" name="action" id="transactionAction" value="" />
                @if(message != null)
                    <div class="alert alert-success p-4 rounded bg-green-100 text-green-800">
                        ${message}
                    </div>
                @endif
                @if(error != null)
                    <div class="alert alert-danger p-4 rounded bg-red-100 text-red-800">
                        ${error}
                    </div>
                @endif
                <!-- Sélection de la cryptomonnaie -->
                <div class="w-96">
                    <label class="label label-text" for="cryptocurrency">Cryptomonnaie :</label>
                    <select class="select" id="cryptocurrency" name="cryptocurrencyId">
                        @for(Crypto crypto : allCrypto)
                            <option value="${crypto.getId()}">${crypto.getName()} (${crypto.getSymbol()})</option>
                        @endfor
                    </select>
                </div>

                <!-- Quantité -->
                <div class="w-96">
                    <label class="label label-text" for="quantity">Quantité :</label>
                    <input name="quantity" type="number" id="quantity" step="0.00000001" min="0.00000001" required class="input" />
                </div>

                <!-- Prix unitaire (pré-rempli) -->
                <div class="w-96">
                    <label class="label label-text" for="price_unit">Prix unitaire :</label>
                    <input type="number" id="price_unit" step="0.00000001" readonly class="input" disabled/>
                </div>

                <!-- Montant total (calcul automatique) -->
                <div class="w-96">
                    <label class="label label-text" for="total_amount">Montant total :</label>
                    <input type="number" id="total_amount" step="0.00000001" readonly class="input" disabled/>
                </div>

                <!-- Bouton de validation -->
                <div class="flex gap-1">
                    <button type="submit" class="btn btn-success w-1/2 text-white" onclick="setTransactionType('buy')">Acheter</button>
                    <button type="submit" class="btn btn-outline btn-error w-1/2" onclick="setTransactionType('sell')">Vendre</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function setTransactionType(type) {
            document.getElementById("transactionAction").value = type;
        }
    </script>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const cryptoSelect = document.getElementById("cryptocurrency");
            const quantityInput = document.getElementById("quantity");
            const priceUnitInput = document.getElementById("price_unit");
            const totalAmountInput = document.getElementById("total_amount");

            // Fonction pour récupérer le prix unitaire via l'API
            async function fetchCryptoPrice(cryptoId) {
                try {
                    const response = await fetch('/crypto/last-price/' + cryptoId);
                    if (!response.ok) {
                        throw new Error("Erreur lors de la récupération du prix");
                    }
                    const data = await response.json();
                    return parseFloat(data.price); // Convertir le prix en nombre
                } catch (error) {
                    console.error("Erreur :", error);
                    return 0; // Retourner 0 en cas d'erreur
                }
            }

            // Fonction pour mettre à jour le prix unitaire et le montant total
            async function updatePriceAndTotal() {
                const selectedCryptoId = cryptoSelect.value; // Récupère l'ID de la cryptomonnaie sélectionnée
                const priceUnit = await fetchCryptoPrice(selectedCryptoId);
                priceUnitInput.value = priceUnit.toFixed(8); // Afficher le prix avec 8 décimales

                const quantity = parseFloat(quantityInput.value) || 0;
                totalAmountInput.value = (quantity * priceUnit).toFixed(8); // Calculer et afficher le montant total
            }

            // Écouter les changements de sélection de cryptomonnaie
            cryptoSelect.addEventListener("change", updatePriceAndTotal);

            // Écouter les changements de quantité
            quantityInput.addEventListener("input", updatePriceAndTotal);

            // Initialiser les valeurs au chargement de la page
            updatePriceAndTotal();

            // Mise à jour toutes les 10 secondes
            setInterval(updatePriceAndTotal, 10000);
        });
    </script>

`, user = user)