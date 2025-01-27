/** @type {import('tailwindcss').Config} */

module.exports = {
    content: [
        "../main/resources/templates/**/*.{html,js}",
        "../main/resources/static/**/*.{html,js}",
        "./node_modules/flowbite/**/*.js",
    ],
    theme: {
        extend: {},
    },
    plugins: [
        require('flowbite/plugin')
    ]
}
