import PageBanner from "@/components/page/PageBanner";
import Link from "next/link";

export default function Home() {
  return (
    <div className="space-y-8">
      <PageBanner />

      <section className="mx-[54vh]">
        <div className="bg-white py-6 px-9 rounded-t-md rounded-b-xl drop-shadow-md/20 space-y-8">
          <div>
            <h1 className="text-neutral-700 text-[36px] font-black uppercase">
              Mineplex Website Remastered
            </h1>

            <div className="bg-linear-to-r from-amber-500 to-transparent h-2" />
          </div>

          <div className="text-[20px] font-roboto font-light space-y-3">
            <p>
              Hello visitors,
            </p>

            <p>
              today we have finished Mineplex Website Remastered, which we built only partially (with Home, Forums and Games categories only), 
              heavily inspired by the original Mineplex website shortly before its shutdown, specifically its 2021 version.
            </p>

            <p>
              The design was partially adjusted along with the overall development of the site by Smirkyea in Next.js 16, 
              and the backend was created by JustDrven using the Java framework Quarkus.
            </p>

            <p>
              This is not an official Mineplex website. Only assets from their previous site were used, as mentioned above.
              This project and its code are not a collaboration and do not belong to Mineplex.
            </p>
          </div>

          <div className="space-y-4">
            <div className="bg-linear-to-r from-neutral-200 to-transparent h-1" />

            <div className="flex items-start justify-between">
              <h1 className="text-[18px] font-light">
                Posted by <span className="font-normal">Admin</span>
              </h1>

              <Link href={"/"} className="bg-amber-500 border-b-3 border-b-amber-900 py-2 px-8 text-white text-shadow-sm/20 rounded-t-sm rounded-b-lg transition-colors duration-200 hover:bg-amber-600">
                View More
              </Link>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
