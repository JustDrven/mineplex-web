import Navbar from "@/components/navigation/Navbar"

const Layout = ({ children }: { children: React.ReactNode }) => {
  return (
    <>
      <Navbar />

      <div className="relative min-h-screen overflow-hidden before:absolute before:inset-0 before:bg-[url('/images/background.png')] before:bg-repeat before:bg-size-[auto_40%] before:-z-10">
        {children}
      </div>
    </>
  )
}

export default Layout