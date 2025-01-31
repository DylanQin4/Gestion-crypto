<!DOCTYPE html>
<html lang="en" data-theme="mytheme">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>True Wallet</title>
    <link rel="stylesheet" href="/css/tailwind-output.css">
    <link rel="stylesheet" href="/apexcharts/src/assets/apexcharts.css">
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- Inclure jQuery -->
</head>
<body class="bg-gray-100">
<div class="min-h-screen">
    <!-- Navigation -->
    @template.fragments.navbar()

    <!-- Sidebar -->
    @template.fragments.sidebar()

    <!-- Main content -->
    <div class="pl-64 min-h-full">
        <div class="p-6 max-w-7xl sm:px-6 lg:px-8">
            <div id="candlestickChart"></div>
        </div>
    </div>
</div>
</body>

<script src="/flyonui/flyonui.js"></script>

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
                        text: "Candlestick Chart avec ApexCharts",
                        align: "left"
                    },
                    xaxis: {
                        type: "category",
                        labels: {
                            rotate: -45 // Rotation des étiquettes pour une meilleure lisibilité
                        },
                        tooltip: {
                            enabled: true
                        }
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