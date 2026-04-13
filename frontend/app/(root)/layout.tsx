import Footer from "@/components/navigation/Footer"
import Navbar from "@/components/navigation/Navbar"

const Layout = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <Navbar />

      <div className="relative min-h-screen space-y-22 overflow-hidden before:absolute before:inset-0 before:bg-[url('/images/background.png')] before:bg-repeat before:bg-size-[auto_40%] before:-z-10">
        <div>
          {children}
        </div>

        <Footer />
      </div>
    </>
  )
}

export default Layout