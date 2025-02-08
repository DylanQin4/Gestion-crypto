@param String error

@template.layout.auth(content = @`
    <div class="card">
        <form action="/auth/validate-pin" method="post">
            <div class="card-header w-full flex flex-col items-center justify-center">
                <h2 class="card-title">Code de validation</h2>
                <span class="text-xs text-gray-700">Un code de validation vous a été envoyé.</span>
            </div>
            <div class="card-body flex flex-col gap-2">
                @if(error != null)
                    <div class="max-w-80 mb-2 alert alert-danger p-4 rounded rounded-b bg-red-100 text-red-800 line-clamp-2">
                        ${error}
                    </div>
                @endif
                <div class="flex space-x-3 rtl:space-x-reverse" data-pin-input>
                    <input type="text" class="pin-input" aria-label="pin input" data-pin-input-item name="pin" maxlength="1" autofocus required/>
                    <input type="text" class="pin-input" aria-label="pin input" data-pin-input-item name="pin" maxlength="1" required/>
                    <input type="text" class="pin-input" aria-label="pin input" data-pin-input-item name="pin" maxlength="1" required/>
                    <input type="text" class="pin-input" aria-label="pin input" data-pin-input-item name="pin" maxlength="1" required/>
                    <input type="text" class="pin-input" aria-label="pin input" data-pin-input-item name="pin" maxlength="1" required/>
                    <input type="text" class="pin-input" aria-label="pin input" data-pin-input-item name="pin" maxlength="1" required/>
                </div>
                <button type="submit" class="btn btn-primary mt-6">Valider</button>
            </div>
<%--            <div class="card-footer w-full flex items-center justify-center">--%>
<%--                <a href="/auth/resend" class="link">Renvoyer le code</a>--%>
<%--            </div>--%>
        </form>
    </div>
`)