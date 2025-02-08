@import mg.itu.cloud.user.User

@param gg.jte.Content content
@param User user

<!DOCTYPE html>
<html lang="en" data-theme="mytheme">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>True Wallet</title>
        <link rel="stylesheet" href="/css/tailwind-output.css">
    </head>
    <body class="bg-gray-100">
        <div class="min-h-screen">
            <!-- Navigation -->
            @template.fragments.navbar(user = user)

            <!-- Sidebar -->
            @template.fragments.sidebar(user = user)

            <!-- Main content -->
            <div class="pl-64 h-full">
                <div class="p-6">
                    ${content}
                </div>
            </div>
        </div>
    </body>
    <script src="/flyonui/flyonui.js"></script>
    <script src="/flyonui/dist/js/dropdown.js"></script>
</html>