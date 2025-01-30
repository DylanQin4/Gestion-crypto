@template.layout.main(content = @`
    <div class="max-w-4xl mx-auto space-y-8">
        <h1 class="text-3xl font-bold text-center">Gestion des fonds</h1>

        <div class="grid gap-8 md:grid-cols-2">
            <div class="bg-white rounded-lg shadow-md p-6">
                <div class="flex flex-col space-y-2">
                    <h2 class="text-xl font-semibold">Dépôt</h2>
                    <p class="text-gray-500">Ajouter des fonds à votre compte</p>
                </div>
                <form>
                    <div class="mt-4 space-y-4">
                        <div>
                            <label for="deposit-amount" class="block text-sm font-medium text-gray-700">Montant</label>
                            <input type="number" id="deposit-amount" placeholder="Entrer le montant"
                                   class="mt-1 p-2 border rounded-md w-full focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
                        </div>
                        <button type="submit"
                                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                            Déposer
                        </button>
                    </div>
                </form>
            </div>

            <div class="bg-white rounded-lg shadow-md p-6">
                <div class="flex flex-col space-y-2">
                    <h2 class="text-xl font-semibold">Retrait</h2>
                    <p class="text-gray-500">Retirer des fonds de votre compte</p>
                </div>
                <form>
                    <div class="mt-4 space-y-4">
                        <div>
                            <label for="withdrawal-amount" class="block text-sm font-medium text-gray-700">Montant</label>
                            <input type="number" id="withdrawal-amount" placeholder="Entrer le montant"
                                   class="mt-1 p-2 border rounded-md w-full focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
                        </div>
                        <button type="submit"
                                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                            Retirer
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex flex-col space-y-2">
                <h2 class="text-xl font-semibold">Historique des requêtes</h2>
                <p class="text-gray-500">Vos récentes demandes de dépôt et de retrait</p>
            </div>
            <table class="min-w-full mt-4 border-collapse">
                <thead>
                <tr>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Type</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Montant</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Statut</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="px-4 py-2 border">Dépôt</td>
                    <td class="px-4 py-2 border">$1000.00</td>
                    <td class="px-4 py-2 border">En attente de validation</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border">Retrait</td>
                    <td class="px-4 py-2 border">$500.00</td>
                    <td class="px-4 py-2 border">Validé</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border">Dépôt</td>
                    <td class="px-4 py-2 border">$2000.00</td>
                    <td class="px-4 py-2 border">Refusé</td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border">Retrait</td>
                    <td class="px-4 py-2 border">$300.00</td>
                    <td class="px-4 py-2 border">En attente de validation</td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
`)