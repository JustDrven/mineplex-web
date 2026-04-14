import Footer from "@/components/navigation/Footer"
import Navbar from "@/components/navigation/Navbar"

const Layout = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <Navbar />

      <div className="relative flex min-h-screen flex-col overflow-hidden space-y-22 before:absolute before:inset-0 before:bg-[url('/images/background.png')] before:bg-repeat before:bg-size-[auto_40%] before:-z-10">
        <div className="flex-1">
          {children}
        </div>

        <Footer />
      </div>
    </>
  )
}

export default Layout
