@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>True Wallet</title>
<%--    <script src="https://cdn.tailwindcss.com"></script>--%>
    <link rel="stylesheet" href="/css/tailwind-output.css">
</head>
<body class="bg-gray-100">
<div class="min-h-full">
    <!-- Navigation -->
    @template.fragments.navbar()

    <!-- Sidebar -->
    @template.fragments.sidebar()

    <!-- Main content -->
    <main>
        <div class="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
            <!-- https://jte.gg/syntax/#content -->
            ${content}
        </div>
    </main>
</div>
</body>
<script src="/flyonui/flyonui.js"></script>
<script src="/flyonui/dist/js/dropdown.js"></script>
</html>