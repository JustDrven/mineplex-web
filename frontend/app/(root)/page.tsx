'use client'

import ChangelogContainer from "@/components/container/ChangelogContainer";
import PageBanner from "@/components/page/PageBanner";

export default function Home() {
  return (
    <div className="space-y-8">
      <PageBanner />

      <>
        <ChangelogContainer 
          id={1}
          title="Mineplex Website Remastered"
          description={[
            "Hello visitors,",
            "today we have finished Mineplex Website Remastered, which we built only partially (with Home, Forums and Games categories only), heavily inspired by the original Mineplex website shortly before its shutdown, specifically its 2021 version.",
            "The design was partially adjusted along with the overall development of the site by Smirkyea in Next.js 16, and the backend created by JustDrven using the Java framework Quarkus.",
            "This is not an official Mineplex website. Only assets from their previous site were used, as mentioned above. This project and its code are not a collaboration and do not belong to Mineplex."
          ]}
          createdBy="Smirkyea"
          createdAt={1776101441520}
        />
      </>
    </div>
  );
}
