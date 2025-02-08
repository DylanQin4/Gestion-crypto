@param String error

@template.layout.auth(content = @`
    <div class="card">
        <form action="/auth/login" method="post">
            <div class="card-header w-full flex items-center justify-center">
                <h2 class="card-title">Connexion</h2>
            </div>
            <div class="card-body flex flex-col gap-2">
                @if(error != null)
                    <div class="w-96 alert alert-danger p-4 rounded rounded-b bg-red-100 text-red-800">
                        ${error}
                    </div>
                @endif
                <div class="w-96">
                    <label class="label label-text" for="email">Email</label>
                    <input type="email" placeholder="" class="input" id="email" name="email" value="rinchacassien@gmail.com"/>
                    <span class="error-message">Error</span>
                </div>
                <div class="w-96">
                    <label class="label label-text" for="password">Mot de passe</label>
                    <input type="password" placeholder="" class="input" id="password" name="password" value="1234"/>
                </div>
                <button type="submit" class="btn btn-primary mt-6">Se connecter</button>
            </div>
            <div class="card-footer w-full flex items-center justify-center">
                <a href="/auth/register" class="link">Créer un compte</a>
            </div>
        </form>
    </div>
`)