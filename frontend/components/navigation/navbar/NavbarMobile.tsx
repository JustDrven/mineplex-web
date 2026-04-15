import { Sheet, SheetClose, SheetContent, SheetHeader, SheetTitle, SheetTrigger } from "@/components/ui/sheet"
import { navLinks } from "@/constants/navLinks"
import { ROUTES } from "@/constants/routes"
import { IconMenu2, IconX } from "@tabler/icons-react"
import Image from "next/image"
import Link from "next/link"

const NavbarMobile = () => {
  return (
    <Sheet>
      <SheetTrigger className="bg-neutral-600 border-b-3 border-b-neutral-900 py-2 px-3 text-white text-[15px] text-shadow-sm/20 uppercase rounded-t-sm rounded-b-lg cursor-pointer transition-colors duration-200 hover:bg-neutral-700">
        <IconMenu2 />
      </SheetTrigger>
      <SheetContent showCloseButton={false} className="w-screen! bg-neutral-900 border-none">
        <SheetHeader>
          <SheetTitle className="flex items-center justify-between">
            <SheetClose asChild>
              <Link href={ROUTES.HOME} className="link-icon block">
                <Image src="/images/logo.png" alt="Mineplex" height={200} width={200} />
              </Link>
            </SheetClose>

            <SheetClose className="bg-neutral-800 h-8 w-8 text-neutral-300 cursor-pointer flex flex-col items-center justify-center rounded-lg transition-colors duration-200 hover:bg-neutral-700/60 hover:text-white">
              <IconX size={18} />
            </SheetClose>
          </SheetTitle>

          <div className="mt-8 flex flex-col gap-2">
            {navLinks.map((link, idx) => (
              <SheetClose key={idx} asChild>
                <Link href={link.route} className="bg-neutral-800 py-3.5 text-white text-[16px] text-center font-roboto rounded-xl transition-colors duration-200 hover:bg-neutral-700/60">
                  {link.label}
                </Link>
              </SheetClose>
            ))}

            <SheetClose asChild>
              <Link href={ROUTES.PLAY} className="bg-amber-600 py-3.5 text-white text-[16px] text-shadow-sm/30 text-center font-roboto rounded-xl transition-colors duration-200 hover:bg-amber-700">
                Play Now!
              </Link>
            </SheetClose>
          </div>
        </SheetHeader>
      </SheetContent>
    </Sheet>
  )
}

export default NavbarMobile
