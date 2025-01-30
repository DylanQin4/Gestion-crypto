@template.layout.main(content = @`
    <div class="max-w-4xl mx-auto">
        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex flex-col space-y-2">
                <h2 class="text-xl font-semibold">Cryptocurrency Prices</h2>
                <p class="text-gray-500">Real-time prices of top cryptocurrencies</p>
            </div>
            <div class="mb-4 mt-4">
                <label for="filter" class="block text-sm font-medium text-gray-700">Filter</label>
                <input type="text" id="filter" placeholder="Filter by name or symbol"
                       class="mt-1 p-2 border rounded-md w-full focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
            </div>
            <table class="min-w-full mt-4 border-collapse">
                <thead>
                <tr>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Symbol</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Name</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">Price</th>
                    <th class="px-4 py-2 text-left bg-gray-100 border">24h Change</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="px-4 py-2 border font-medium">BTC</td>
                    <td class="px-4 py-2 border">Bitcoin</td>
                    <td class="px-4 py-2 border">$30,000.00</td>
                    <td class="px-4 py-2 border flex items-center text-green-600">
                        <i data-lucide="arrow-up" class="w-4 h-4 mr-1 inline-block"></i> 2.5%
                    </td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border font-medium">ETH</td>
                    <td class="px-4 py-2 border">Ethereum</td>
                    <td class="px-4 py-2 border">$2,000.00</td>
                    <td class="px-4 py-2 border flex items-center text-red-600">
                        <i data-lucide="arrow-down" class="w-4 h-4 mr-1 inline-block"></i> 1.2%
                    </td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border font-medium">ADA</td>
                    <td class="px-4 py-2 border">Cardano</td>
                    <td class="px-4 py-2 border">$0.50</td>
                    <td class="px-4 py-2 border flex items-center text-green-600">
                        <i data-lucide="arrow-up" class="w-4 h-4 mr-1 inline-block"></i> 5.7%
                    </td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border font-medium">DOT</td>
                    <td class="px-4 py-2 border">Polkadot</td>
                    <td class="px-4 py-2 border">$7.00</td>
                    <td class="px-4 py-2 border flex items-center text-red-600">
                        <i data-lucide="arrow-down" class="w-4 h-4 mr-1 inline-block"></i> 0.8%
                    </td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border font-medium">XRP</td>
                    <td class="px-4 py-2 border">Ripple</td>
                    <td class="px-4 py-2 border">$0.60</td>
                    <td class="px-4 py-2 border flex items-center text-green-600">
                        <i data-lucide="arrow-up" class="w-4 h-4 mr-1 inline-block"></i> 1.5%
                    </td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border font-medium">DOGE</td>
                    <td class="px-4 py-2 border">Dogecoin</td>
                    <td class="px-4 py-2 border">$0.08</td>
                    <td class="px-4 py-2 border flex items-center text-green-600">
                        <i data-lucide="arrow-up" class="w-4 h-4 mr-1 inline-block"></i> 10.2%
                    </td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border font-medium">LINK</td>
                    <td class="px-4 py-2 border">Chainlink</td>
                    <td class="px-4 py-2 border">$15.00</td>
                    <td class="px-4 py-2 border flex items-center text-red-600">
                        <i data-lucide="arrow-down" class="w-4 h-4 mr-1 inline-block"></i> 2.3%
                    </td>
                </tr>
                <tr>
                    <td class="px-4 py-2 border font-medium">UNI</td>
                    <td class="px-4 py-2 border">Uniswap</td>
                    <td class="px-4 py-2 border">$5.50</td>
                    <td class="px-4 py-2 border flex items-center text-green-600">
                        <i data-lucide="arrow-up" class="w-4 h-4 mr-1 inline-block"></i> 3.7%
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
`)