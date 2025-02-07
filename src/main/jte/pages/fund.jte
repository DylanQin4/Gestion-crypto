@import mg.itu.cloud.fund.FundTransaction
@import java.util.List
@import mg.itu.cloud.user.User

@param User user

@param List<FundTransaction> transactions
@param String message
@param String error

@template.layout.main(content = @`
    <div class="max-w-4xl mx-auto space-y-8">
        <h1 class="text-3xl font-bold text-center">Gestion des fonds</h1>

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

        <div class="grid gap-8 md:grid-cols-2">
            <div class="bg-white rounded-lg shadow-md p-6">
                <div class="flex flex-col space-y-2">
                    <h2 class="text-xl font-semibold">Dépôt</h2>
                    <p class="text-gray-500">Ajouter des fonds à votre compte</p>
                </div>
                <form action="/fund-management/deposit" method="post">
                    <div class="mt-4 space-y-4">
                        <div>
                            <label for="deposit-amount" class="block text-sm font-medium text-gray-700">Montant</label>
                            <input name="amount" type="number" id="deposit-amount" placeholder="Entrer le montant"
                                   class="mt-1 p-2 border rounded-md w-full input" />
                        </div>
                        <button type="submit"
                                class="btn btn-success text-white">
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
                <form action="/fund-management/withdraw" method="post">
                    <div class="mt-4 space-y-4">
                        <div>
                            <label for="withdrawal-amount" class="block text-sm font-medium text-gray-700">Montant</label>
                            <input name="amount" type="number" id="withdrawal-amount" placeholder="Entrer le montant"
                                   class="mt-1 p-2 border rounded-md w-full input" />
                        </div>
                        <button type="submit"
                                class="btn btn-outline btn-error">
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
                    <th class="px-4 py-2 text-left bg-gray-100 border">Date</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Statut</th>
                </tr>
                </thead>
                <tbody>
                @for(FundTransaction transaction : transactions)
                    <tr>
                        <td class="px-4 py-2 border">${transaction.getTransactionType().getName()}</td>
                        <td class="px-4 py-2 border">${transaction.getAmount()}</td>
                        <td class="px-4 py-2 border">${transaction.getTransactionDate().toString()}</td>
                        <td class="px-4 py-2 border">${transaction.getStatus()}</td>
                    </tr>
                @endfor

                </tbody>
            </table>
        </div>
    </div>
`, user = user)