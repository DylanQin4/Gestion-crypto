@import mg.itu.cloud.wallet.WalletResponse
@import mg.itu.cloud.wallet.VUsersCryptoQuantity

@param WalletResponse wallet

@template.layout.main(content = @`
    <div class="max-w-3xl mx-auto">
        <div class="rounded-lg shadow-xl p-6 mb-6 text-white" style=" background: rgb(0,224,214);
background: linear-gradient(328deg, rgba(0,224,214,1) 0%, rgba(163,0,247,1) 58%); ">
            <h1 class="text-3xl font-bold mb-2">Portefeuille</h1>
            <div class="flex justify-between items-end">
                <div>
                    <p class="text-sm opacity-75">Fond</p>
                    <p class="text-4xl font-bold">${wallet.totalFundsAmount()}</p> </div>
                <div class="text-right">
<%--                    <p class="text-sm opacity-75">Global Change</p>--%>
                    <p class="text-2xl font-semibold flex items-center justify-end text-green-300">
<%--                        <i data-lucide="arrow-up" class="w-6 h-6 mr-1 inline-block"></i> 2.5%--%>
                    </p>
                </div>
            </div>
        </div>

        <div class="grid gap-6 md:grid-cols-2">
            @for(VUsersCryptoQuantity crypto : wallet.usersCryptoQuantityList())
                <div class="bg-white rounded-lg shadow-md p-4">
                    <div class="flex flex-row items-center justify-between space-y-0 pb-2">
                        <h3 class="text-sm font-medium">${crypto.getCryptoName()}</h3>
                        <div class="text-sm font-bold flex items-center text-green-600">
<%--                            <i data-lucide="arrow-up" class="w-4 h-4 mr-1 inline-block"></i> 1.2%--%>
                        </div>
                    </div>
                    <div>
                        <div id="crypto-${crypto.getCryptoId()}" class="text-2xl font-bold" data-quantity="${crypto.getTotalQuantity()}">
                            ...
                        </div>
                        <p class="text-xs text-gray-500">${crypto.getTotalQuantity() + " " + crypto.getCryptoSymbol()}</p>
                    </div>
                </div>
            @endfor
        </div>
    </div>

    <script>
        // Fonction pour récupérer le prix en temps réel d'une cryptomonnaie
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

        // Fonction pour mettre à jour le prix en temps réel pour chaque cryptomonnaie
        async function updateCryptoPrices() {
            // Sélectionner tous les éléments qui affichent le prix
            const cryptoElements = document.querySelectorAll('[id^="crypto-"]');

            for (const element of cryptoElements) {
                const cryptoId = element.id.split('-')[1]; // Extraire l'ID de la cryptomonnaie
                const quantity = parseFloat(element.dataset.quantity); // Récupérer la quantité depuis l'attribut data-quantity

                // Récupérer le prix en temps réel
                const price = await fetchCryptoPrice(cryptoId);

                // Calculer le total (prix * quantité)
                const totalValue = price * quantity;

                // Mettre à jour le contenu de l'élément
                element.textContent = totalValue.toFixed(2); // Afficher avec 2 décimales
            }
        }

        // Appeler la fonction pour mettre à jour les prix au chargement de la page
        document.addEventListener('DOMContentLoaded', updateCryptoPrices);

        // Optionnel : Mettre à jour les prix toutes les 30 secondes
        setInterval(updateCryptoPrices, 30000);
    </script>
`)