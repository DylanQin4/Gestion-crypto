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
    @template.fragments.navbar()

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
                        <h2 class="text-3xl font-bold mb-2">Crypto 1</h2>
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
    }
</script>

@raw
<script>
    $(document).ready(function() {
        // Remplacez {idCrypto} par l'ID réel de la crypto-monnaie
        var idCrypto = 1;
        $.ajax({
            url: '/crypto/evolution/data/' + idCrypto,
            method: 'GET',
            success: function(candleData) {
                console.log("Données reçues pour ApexCharts:", candleData);

                var options = {
                    series: [{
                        data: candleData // Assurez-vous que le format est correct
                    }],
                    chart: {
                        type: "candlestick",
                        height: 350,
                        zoom: {
                            enabled: true, // Active le zoom
                            type: 'x' // Permet le zoom horizontal
                        }
                    },
                    title: {
                        text: "Crypto 1",
                        align: "center"
                    },
                    xaxis: {
                        type: 'datetime'
                    },
                    yaxis: {
                        tooltip: { enabled: true }
                    }
                };

                var chart = new ApexCharts(document.querySelector("#candlestickChart"), options);
                chart.render();
            },
            error: function(xhr, status, error) {
                console.error("Erreur lors de la récupération des données:", error);
            }
        });
    });
</script>
@endraw

</html>