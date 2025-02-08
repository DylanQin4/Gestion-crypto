@import mg.itu.cloud.user.User

@param User user
@param String successMessage
@param String errorMessage

@template.layout.main(content = @`
    <div class="max-w-4xl mx-auto space-y-8 p-4">
        <h1 class="text-3xl font-bold text-center text-gray-800 mb-4">Profil de ${user.getName()}</h1>

        @if(successMessage != null)
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative" role="alert">
                <strong class="font-bold">Succès !</strong>
                <span class="block sm:inline ml-2">${successMessage}</span>
            </div>
        @endif

        @if(errorMessage != null)
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
                <strong class="font-bold">Erreur !</strong>
                <span class="block sm:inline ml-2">${errorMessage}</span>
            </div>
        @endif

        <div class="bg-white rounded-lg shadow p-6">
            <p class="text-gray-600">Nom : ${user.getName()}</p>
        </div>

        <div class="bg-white rounded-lg shadow p-6">
            <h2 class="text-xl font-semibold text-gray-700 mb-2">Modifier votre nom</h2>
            <form action="/user/update" method="post" class="space-y-4">
                <input type="hidden" name="id" value="${user.getId()}">
                <input type="text" name="name" value="${user.getName()}" required
                       class="w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                       placeholder="Nouveau nom">
                <button type="submit"
                        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                    Mettre à jour
                </button>
            </form>
        </div>
    </div>
`)