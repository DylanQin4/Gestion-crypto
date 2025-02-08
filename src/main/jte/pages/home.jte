@import mg.itu.cloud.user.User

@param User user
@param String username

@template.layout.main(content = @`
    <div class="bg-white shadow rounded-lg p-6">
        <h1 class="text-2xl font-bold mb-4">Welcome, ${username}!</h1>
        <p class="text-gray-600">This is your dashboard. Here you can view your recent activity and manage your account.</p>
    </div>
`, user = user)