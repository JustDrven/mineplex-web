import { footerLinks } from "@/constants/footerLinks"
import { ROUTES } from "@/constants/routes"
import { socialsList } from "@/constants/socials"
import { ColorUtil } from "@/utils/ColorUtil"
import Image from "next/image"
import Link from "next/link"

const Footer = () => {
  return (
    <div className="text-white font-roboto">
      <div className="relative h-[32vh] w-full">
        <Image src="/images/footer.png" alt="Footer" fill draggable={false} className="object-cover" />
      </div>

      <div className="bg-[#262326] pb-5 px-3 md:px-[4vh] lg:px-[8vh] xl:px-[18vh] 2xl:px-[36vh] space-y-8 lg:space-y-16">
        <div className="flex max-lg:flex-col items-start justify-between max-lg:gap-y-2">
          <Link href={ROUTES.HOME} className="link-icon">
            <Image src="/icons/icon.png" alt="Mineplex" height={72} width={72} />
          </Link>

          <ul className="grid grid-cols-2 md:grid-cols-3 items-start gap-y-12 gap-x-24 md:gap-x-[16vh] max-lg:px-1.5 pt-3">
            {footerLinks.map((c, idx) => (
              <li key={idx} className="space-y-5">
                <h1 className="text-amber-400 text-[18px] font-bold">
                  {c.title}
                </h1>

                <ul className="space-y-2">
                  {c.links.map((link, idz) => (
                    <li key={idz}>
                      <Link href={link.route} className="transition-colors duration-200 hover:text-amber-300">
                        {link.label}
                      </Link>
                    </li>
                  ))}
                </ul>
              </li>
            ))}
          </ul>

          <div className="max-lg:mx-auto max-lg:pt-8 pt-3 flex flex-col gap-3">
            <Link href={ROUTES.PLAY} className="bg-amber-500 border-b-3 border-b-amber-900 py-3 px-8 text-white text-center text-shadow-sm/20 font-medium rounded-t-sm rounded-b-lg transition-colors duration-200 hover:bg-amber-600">
              Join Mineplex!
            </Link>

            <div className="flex items-center gap-2">
              {socialsList.map((social, idx) => {
                const Icon = social.icon

                return (
                  <Link href={social.href} target="_blank" key={idx} className={`${ColorUtil.getSocialColor[social.color]} bg-neutral-700 h-9 w-9 text-neutral-300 flex items-center justify-center rounded-t-sm rounded-b-lg transition-colors hover:text-white`}>
                    <Icon size={26} />
                  </Link>
                )
              })}
            </div>
          </div>
        </div>

        <p className="text-neutral-500 text-center">
          &copy; {new Date().getFullYear()}{" "} Mineplex Network Remastered - We&apos;re not affiliated with Mineplex
        </p>
      </div>
    </div>
  )
}

export default Footer
