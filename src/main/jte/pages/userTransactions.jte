@import mg.itu.cloud.user.UserCryptoTransaction
@import java.util.List
@import mg.itu.cloud.user.User

@param User user

@param List<UserCryptoTransaction> transactions
@param String message
@param String error

@template.layout.main(content = @`
    <div class="max-w-4xl mx-auto space-y-8">
        <h1 class="text-3xl font-bold text-center">Transactions des utilisateurs en cryptomonnaie</h1>

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

        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex flex-col space-y-2">
                <h2 class="text-xl font-semibold">Historique des transactions</h2>
                <p class="text-gray-500">Détails des transactions de crypto-monnaies des utilisateurs</p>
            </div>
            <table class="min-w-full mt-4 border-collapse">
                <thead>
                <tr>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Nom de l'utilisateur</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Email</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Cryptomonnaie</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Type de transaction</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Quantité</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Prix unitaire</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Montant total</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Date de la transaction</th>
                </tr>
                </thead>
                <tbody>
                @for(UserCryptoTransaction transaction : transactions) {
                    <tr>
                        <td class="px-4 py-2 border">${transaction.getUserName()}</td>
                        <td class="px-4 py-2 border">${transaction.getUserEmail()}</td>
                        <td class="px-4 py-2 border">${transaction.getCryptocurrencyName()} (${transaction.getCryptocurrencySymbol()})</td>
                        <td class="px-4 py-2 border">${transaction.getTransactionType()}</td>
                        <td class="px-4 py-2 border">${transaction.getQuantity()}</td>
                        <td class="px-4 py-2 border">${transaction.getPriceUnit()}</td>
                        <td class="px-4 py-2 border">${transaction.getTotalAmount()}</td>
                        <td class="px-4 py-2 border">${transaction.getTransactionDate()}</td>
                    </tr>
                } @endfor
                </tbody>
            </table>
        </div>
    </div>
`, user = user)
