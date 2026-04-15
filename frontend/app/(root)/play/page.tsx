import PageContainer from "@/components/page/PageContainer";
import Link from "next/link";

export const metadata = {
  title: "Play",
};

const Page = () => {
  return (
    <PageContainer title="How to join the Mineplex Network?">
      <div className="space-y-8">
        <b className="block">
          Hello Mineplexer, below you will find a guide on how to join the Mineplex Network:
        </b>

        <ul className="ml-6 list-disc space-y-2">
          <li>
            Launch Minecraft version <b>1.8.9 - 1.26</b>. You must own a purchased version of the game as we currently do not support cracked launchers.
          </li>

          <li>
            Click on &quot;<b>Multiplayer</b>&quot;, then &quot;<b>Add Server</b>&quot;, and enter the address &quot;<b>us.mineplex.com</b>&quot; in the &quot;<b>Server Address</b>&quot; field.
          </li>

          <li>
            Click &quot;<b>Done</b>&quot;, then double-click the server you just added.
          </li>

          <li>
            Hope you&apos;ll enjoy your time on the server!
          </li>
        </ul>

        <b>
          If you experience connection issues due to potentially blocked regions, please contact us at <Link href="mailto:support@mineplex.com" target="_blank" className="link-tertiary">support@mineplex.com</Link>. 
          We currently host servers only in the United States.
        </b>
      </div>
    </PageContainer>
  )
}

export default Page
