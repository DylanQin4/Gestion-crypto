@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>True Wallet</title>
    <link rel="stylesheet" href="/css/tailwind-output.css">
<%--    <link rel="stylesheet" href="/flyonui/dist/full.css">--%>
</head>
<body class="bg-gray-100">
    <div class="min-h-screen">
        <!-- Navigation -->
        @template.fragments.navbar()

        <!-- Sidebar -->
        @template.fragments.sidebar()

        <!-- Main content -->
        <div class="pl-64 h-full">
            <div class="p-6">0
                <!-- https://jte.gg/syntax/#content -->
                ${content}
            </div>
        </div>
    </div>
</body>
<script src="/flyonui/flyonui.js"></script>
<script src="/flyonui/dist/js/dropdown.js"></script>
</html>