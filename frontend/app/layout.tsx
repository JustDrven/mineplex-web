import type { Metadata } from "next";
import { Oswald, Roboto } from "next/font/google";
import "./globals.css";
import { cn } from "@/lib/utils";
import { Toaster } from "sonner";

const oswald = Oswald({
  variable: "--font-oswald",
  subsets: ["latin"],
});

const roboto = Roboto({
  variable: "--font-roboto",
  subsets: ["latin"]
})

export const metadata: Metadata = {
  title: "Home | Mineplex",
  description: "Mineplex is the largest Minecraft minigame server network in the world, with over 1 million unique players each month. We offer a wide variety of minigames, including classic favorites like Survival Games and SkyWars, as well as new and innovative games that you won't find anywhere else. Join us today and experience the fun and excitement of Mineplex!",
};

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en" className={cn("h-full", "antialiased", oswald.className, roboto.variable)}>
      <body className="min-h-full flex flex-col">
        <Toaster />

        {children}
      </body>
    </html>
  );
}
