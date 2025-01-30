

@template.layout.main(content = @`
    <div class="max-w-3xl mx-auto">
        <div class="bg-gradient-to-r from-blue-500 to-purple-600 rounded-lg shadow-xl p-6 mb-6 text-white">
            <h1 class="text-3xl font-bold mb-2">Your Crypto Portfolio</h1>
            <div class="flex justify-between items-end">
                <div>
                    <p class="text-sm opacity-75">Total Value</p>
                    <p class="text-4xl font-bold">$15,684.23</p> </div>
                <div class="text-right">
                    <p class="text-sm opacity-75">Global Change</p>
                    <p class="text-2xl font-semibold flex items-center justify-end text-green-300">
                        <i data-lucide="arrow-up" class="w-6 h-6 mr-1 inline-block"></i> 2.5%
                    </p>
                </div>
            </div>
        </div>

        <div class="grid gap-6 md:grid-cols-2">
            <div class="bg-white rounded-lg shadow-md p-4">
                <div class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <h3 class="text-sm font-medium">Bitcoin (BTC)</h3>
                    <div class="text-sm font-bold flex items-center text-green-600">
                        <i data-lucide="arrow-up" class="w-4 h-4 mr-1 inline-block"></i> 1.2%
                    </div>
                </div>
                <div>
                    <div class="text-2xl font-bold">$8,750</div>
                    <p class="text-xs text-gray-500">0.5 BTC</p>
                </div>
            </div>

            <div class="bg-white rounded-lg shadow-md p-4">
                <div class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <h3 class="text-sm font-medium">Ethereum (ETH)</h3>
                    <div class="text-sm font-bold flex items-center text-red-600">
                        <i data-lucide="arrow-down" class="w-4 h-4 mr-1 inline-block"></i> 0.8%
                    </div>
                </div>
                <div>
                    <div class="text-2xl font-bold">$5,460</div>
                    <p class="text-xs text-gray-500">4.2 ETH</p>
                </div>
            </div>

            <div class="bg-white rounded-lg shadow-md p-4">
                <div class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <h3 class="text-sm font-medium">Cardano (ADA)</h3>
                    <div class="text-sm font-bold flex items-center text-green-600">
                        <i data-lucide="arrow-up" class="w-4 h-4 mr-1 inline-block"></i> 3.5%
                    </div>
                </div>
                <div>
                    <div class="text-2xl font-bold">$950</div>
                    <p class="text-xs text-gray-500">1000 ADA</p>
                </div>
            </div>

            <div class="bg-white rounded-lg shadow-md p-4">
                <div class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <h3 class="text-sm font-medium">Polkadot (DOT)</h3>
                    <div class="text-sm font-bold flex items-center text-red-600">
                        <i data-lucide="arrow-down" class="w-4 h-4 mr-1 inline-block"></i> 1.5%
                    </div>
                </div>
                <div>
                    <div class="text-2xl font-bold">$524.23</div>
                    <p class="text-xs text-gray-500">100 DOT</p>
                </div>
            </div>
        </div>
    </div>
`)