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
<div class="min-h-screen flex items-center justify-center" style="background: rgb(39,0,158); background: linear-gradient(10deg, rgba(39,0,158,1) 0%, rgba(121,77,255,1) 100%); ">
    <div class="card">
        <form action="/auth/register" method="post">
            <div class="card-header w-full flex items-center justify-center">
                <h2 class="card-title">Inscription</h2>
            </div>
            <div class="card-body flex flex-col gap-2">
                <div class="w-96">
                    <label class="label label-text" for="email">Email</label>
                    <input type="email" placeholder="" class="input" id="email" name="email" />
                    <span class="error-message">Error</span>
                </div>
                <div class="w-96">
                    <label class="label label-text" for="name">Nom</label>
                    <input type="text" placeholder="" class="input" id="name" name="name" />
                    <span class="error-message">Error</span>
                </div>
                <div class="w-96">
                    <label class="label label-text" for="password">Mot de passe</label>
                    <input type="password" placeholder="" class="input" id="password" name="password" />
                </div>
                <button type="submit" class="btn btn-primary mt-6">S'inscrire</button>
            </div>
            <div class="card-footer w-full flex items-center justify-center">
                <a href="/auth/register" class="link">J'ai deja un compte</a>
            </div>
        </form>
    </div>
</div>
</body>
<script src="/flyonui/flyonui.js"></script>
</html>