import { ApolloClient, HttpLink, InMemoryCache } from "@apollo/client";

let forumService: ApolloClient;
let minecraftService: ApolloClient;

const getMinecraftService = (): ApolloClient => {
    if (minecraftService == null)
        minecraftService = loadService(process.env.NEXT_PUBLIC_MINECRAFT_SERVICE || "https://minecraft.mc-svc.mineplex.com");

    return minecraftService;
}

const getForumService = (): ApolloClient => {
    if (forumService == null)
        forumService = loadService(process.env.NEXT_PUBLIC_FORUM_SERVICE || "https://forum.mc-svc.mineplex.com");

    return forumService;
}

const loadService = (url: string | undefined): ApolloClient => {
    if (url == undefined)
        throw new Error("The URL to micro-service can't be undefined!");
    

    return new ApolloClient({
        link: new HttpLink({ uri: (url as string) }),
        cache: new InMemoryCache(),
    });
}

export { getForumService, getMinecraftService };
