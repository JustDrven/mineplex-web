import Footer from "@/components/navigation/Footer"
import Navbar from "@/components/navigation/navbar/Navbar"
import PageContainer from "@/components/page/PageContainer"

export const metadata = {
  title: "Not Found",
};

const NotFound = () => {
  return (
    <>
      <Navbar />

      <div className="relative flex min-h-screen flex-col overflow-hidden space-y-22 before:absolute before:inset-0 before:bg-[url('/images/background.png')] before:bg-repeat before:bg-size-[auto_40%] before:-z-10">
        <div className="flex-1">
          <PageContainer title="We're sssorryyy..." childrenClassName="text-[20px] font-light">
            The page you&apos;re looking for does not exist.
          </PageContainer>
        </div>

        <Footer />
      </div>
    </>
  )
}

export default NotFound
