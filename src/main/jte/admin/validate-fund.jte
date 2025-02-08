@import mg.itu.cloud.fund.FundTransaction
@import java.util.List
@import mg.itu.cloud.user.User

@param User user

@param List<FundTransaction> transactions

@param String error
@param String message

@template.layout.main(content = @`
    <div class="max-w-4xl mx-auto space-y-8">

        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex flex-col space-y-2">
                <h2 class="text-xl font-semibold">Demandes de dépôt et de retrait</h2>
                <p class="text-gray-500">Les récentes demandes de dépôt et de retrait</p>
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
            </div>
            <table class="min-w-full mt-4 border-collapse">
                <thead>
                <tr>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Type</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Montant</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Date</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Statut</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Action</th>
                </tr>
                </thead>
                <tbody>
                @for(FundTransaction transaction : transactions)
                    <tr>
                        <td class="px-4 py-2 border">${transaction.getTransactionType().getName()}</td>
                        <td class="px-4 py-2 border">${transaction.getAmount()}</td>
                        <td class="px-4 py-2 border">${transaction.getTransactionDate().toString()}</td>
                        <td class="px-4 py-2 border">${transaction.getStatus()}</td>
                        <td class="px-4 py-2 border">
                            <a href="/fund-management/requests/validate/${transaction.getId()}" class="btn btn-primary">Valider</a>
                            <a href="/fund-management/requests/invalidate/${transaction.getId()}" class="btn btn-outline btn-error">Supprimer</a>
                        </td>
                    </tr>
                @endfor

                </tbody>
            </table>
        </div>
    </div>
`, user = user)