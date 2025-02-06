@import mg.itu.cloud.fund.FundTransaction
@import java.util.List

@param List<FundTransaction> transactions
@param String message
@param String error

@template.layout.main(content = @`
    <div class="max-w-4xl mx-auto space-y-8">
        <h1 class="text-3xl font-bold text-center">Historique des Transactions</h1>

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
                <h2 class="text-xl font-semibold">Toutes les transactions</h2>
                <p class="text-gray-500">Liste complète des dépôts et retraits</p>
            </div>
            <table class="min-w-full mt-4 border-collapse">
                <thead>
                <tr>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Type</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Montant</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Date</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Utilisateur</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Statut</th>
                </tr>
                </thead>
                <tbody>
                @for(FundTransaction transaction : transactions)
                    <tr>
                        <td class="px-4 py-2 border">${transaction.getTransactionType().getName()}</td>
                        <td class="px-4 py-2 border">${transaction.getAmount()}</td>
                        <td class="px-4 py-2 border">${transaction.getTransactionDate().toString()}</td>
                        <td class="px-4 py-2 border">${transaction.getUser().getName()}</td>
                        <td class="px-4 py-2 border">${transaction.getStatus()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>

        <div class="text-center mt-6">
            <a href="/fund-management" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                Retour à la gestion des fonds
            </a>
        </div>
    </div>
`)
