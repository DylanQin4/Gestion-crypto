/** @type {import('tailwindcss').Config} */

module.exports = {
    content: [
        "../jte/**/*.jte",
        "./src/main/resources/static/**/*.css"
    ],
    theme: {
        extend: {},
    },
    plugins: [
        require("flyonui"),
        require("flyonui/plugin")
    ],
    flyonui: {
        themes: [
            // {
            //     mytheme: {
            //         primary: "#a991f7",
            //         secondary: "#f6d860",
            //         accent: "#37cdbe",
            //         neutral: "#3d4451",
            //         "base-100": "#ffffff",
            //
            //         "--rounded-box": "1rem", // border-radius for large boxes
            //         "--rounded-btn": "0.5rem", // border-radius for buttons
            //         "--rounded-tooltip": "1.9rem", // border-radius for tooltip
            //         "--animation-btn": "0.25s", // button click animation duration
            //         "--animation-input": "0.2s", // input animation duration (e.g., checkboxes, switch)
            //         "--btn-focus-scale": "0.95", // button scale transform on focus
            //         "--border-btn": "1px", // button border width
            //         "--tab-border": "1px", // tab border width
            //         "--tab-radius": "0.5rem" // tab border-radius
            //     }
            // },
            "light"
            // , "dark"
            // , "gourmet"
        ]
    }
}
