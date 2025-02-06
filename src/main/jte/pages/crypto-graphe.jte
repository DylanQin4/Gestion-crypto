@import mg.itu.cloud.crypto.Crypto
@import mg.itu.cloud.user.User

@param User user

@param Crypto crypto
@param Integer id

<!DOCTYPE html>
<html lang="en" data-theme="mytheme">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>True Wallet</title>
    <link rel="stylesheet" href="/css/tailwind-output.css">
    <link rel="stylesheet" href="/apexcharts/src/assets/apexcharts.css">

    <script src="/js/apexcharts.js"></script>
    <script src="/js/jquery-3.6.0.min.js"></script>
</head>
<body class="bg-gray-100">
<div class="min-h-screen">
    <!-- Navigation -->
    @template.fragments.navbar(user = user)

    <!-- Sidebar -->
    @template.fragments.sidebar()

    <!-- Main content -->
    <div class="pl-64 min-h-full">
        <div class="p-6 sm:px-6 lg:px-8">
            <div onclick="goBack()" class="flex items-center gap-1 pb-2 cursor-pointer text-gray-700">
                <img class="h-4 w-6 cursor-pointer" src="/icons/arrow-left.svg"/>
                Retour
            </div>
            <div class="card w-full">
                <div class="card-body">
                    <div class="flex flex-col space-y-2">
                        <h2 class="text-3xl font-bold mb-2">${crypto.getName()}</h2>
                    </div>
                    <div id="candlestickChart"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="/flyonui/flyonui.js"></script>

<script>
    function goBack() {
        if (window.history.length > 1) {
            history.back();
        } else {
            window.location.href = '/crypto';
        }
    }price_history
</script>

@raw
<script>
    $(document).ready(function () {
        const urlPath = window.location.pathname; // "/crypto/evolution/{id}"
        const segments = urlPath.split("/");
        let idCrypto = segments[segments.length - 1];
        console.log("ID de la cryptomonnaie :", idCrypto);
        let lastPriceId = null;
        let chart = null;

        // Fonction pour recuperer les prix historiques et initialiser le graphique
        function loadHistoricalPrices() {
            $.ajax({
                url: '/crypto/prices/' + idCrypto,
                method: 'GET',
                success: function (data) {
                    console.log("Donnees historiques reçues :", data);

                    if (data.length === 0) {
                        console.warn("Aucune donnee disponible.");
                        return;
                    }

                    // Recupère le dernier ID pour les futures mises à jour
                    lastPriceId = data[data.length - 1].id;

                    let candleData = data.map(entry => ({
                        x: new Date(entry.recordDate).getTime(),
                        y: [
                            parseFloat(entry.open),
                            parseFloat(entry.high),
                            parseFloat(entry.low),
                            parseFloat(entry.close)
                        ]
                    }));

                    let options = {
                        series: [{ data: candleData }],
                        chart: {
                            type: "candlestick",
                            height: 350,
                            zoom: { enabled: true, type: 'x' }
                        },
                        title: { text: "Évolution du prix", align: "center" },
                        xaxis: { type: 'datetime' },
                        yaxis: { tooltip: { enabled: true } }
                    };

                    chart = new ApexCharts(document.querySelector("#candlestickChart"), options);
                    chart.render();
                },
                error: function (xhr, status, error) {
                    console.error("Erreur lors de la recuperation des prix historiques :", error);
                }
            });
        }

        // Fonction pour recuperer le prochain prix et l'ajouter au graphique
        function updatePrice() {
            if (lastPriceId === null) return; // Si pas encore de donnees, ne fait rien

            $.ajax({
                url: '/crypto/price/' + idCrypto + '/' + lastPriceId,
                method: 'GET',
                success: function (newPrice) {
                    if (!newPrice || !newPrice.id) {
                        console.warn("Aucun nouveau prix disponible.");
                        return;
                    }

                    console.log("Nouveau prix reçu :", newPrice);
                    lastPriceId = newPrice.id; // Met à jour l'ID du dernier prix

                    let newCandle = {
                        x: new Date(newPrice.recordDate).getTime(),
                        y: [
                            parseFloat(newPrice.open),
                            parseFloat(newPrice.high),
                            parseFloat(newPrice.low),
                            parseFloat(newPrice.close)
                        ]
                    };

                    chart.appendData([{ data: [newCandle] }]);
                },
                error: function (xhr, status, error) {
                    console.error("Erreur lors de la recuperation du nouveau prix :", error);
                }
            });
        }

        // Initialisation du graphique
        loadHistoricalPrices();

        // Mise a jour toutes les 10 secondes
        setInterval(updatePrice, 10000);
    });
</script>
@endraw

</html>