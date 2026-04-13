import { ROUTES } from "./routes"

export const bannersList = [
    {
        title: "Welcome to Mineplex!",
        description: "Mineplex is home for some awesome Minecraft minigames featuring Bridges, Clans, Survival Games, Super Smash Mobs and more!",
        image: "/images/banners/banner-1.png",
        button: {
            label: "Play Now!",
            target: "_self",
            route: ROUTES.HOME,
        }
    },
    {
        title: "Share your thoughts on the Mineplex forums!",
        description: "Share your thoughts on any thing on the Mineplex forums. There you can get some feedback from other players.",
        image: "/images/banners/banner-1.png",
        button: {
            label: "Visit the Forums!",
            target: "_self",
            route: ROUTES.HOME,
        }
    },
    {
        title: "Join the Mineplex Discord server!",
        description: "Join our Discord server, find new teammates or just talk to other players!",
        image: "/images/banners/banner-1.png",
        button: {
            label: "Join Now!",
            target: "_blank",
            route: "https://discord.mineplex.com",
        }
    }
]
