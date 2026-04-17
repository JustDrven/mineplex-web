import Footer from "@/components/navigation/Footer"
import Navbar from "@/components/navigation/navbar/Navbar"

const Layout = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <Navbar />

      <div className="flex min-h-screen flex-col overflow-hidden space-y-22 bg-[url('/images/background.png')] bg-repeat bg-size-[56vh]">
        <div className="flex-1">
          {children}
        </div>

        <Footer />
      </div>
    </>
  )
}

export default Layout