"use client"

import { useRef, useEffect } from "react"
import Image from "next/image"
import { gsap } from "gsap"
import { toast } from "sonner"
import { navLinks } from "@/constants/navLinks"
import Link from "next/link"
import { usePathname } from "next/navigation"
import { ROUTES } from "@/constants/routes"
import NavbarMobile from "./NavbarMobile"

const LAYERS = [
  { src: "/images/headers/layer-0.png", alt: "layer 0" },
  { src: "/images/headers/layer-1.png", alt: "layer 1" },
  { src: "/images/headers/layer-2.png", alt: "layer 2" },
  { src: "/images/headers/layer-3.png", alt: "layer 3" },
  { src: "/images/headers/layer-4.png", alt: "layer 4" },
]

const MAX_MOVE = 40

const copyText = (text: string) => {
  navigator.clipboard.writeText(text)
  toast.info("IP copied to clipboard!")
}

const Navbar = () => {
  const pathname = usePathname()

  const containerRef = useRef<HTMLDivElement>(null)
  const layerRefs = useRef<(HTMLDivElement | null)[]>([])
  const sentinelRef = useRef<HTMLDivElement>(null)
  const stickyNavRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    const sentinel = sentinelRef.current
    const stickyNav = stickyNavRef.current

    let isMobile = false

    if (!sentinel || !stickyNav) return

    gsap.set(stickyNav, { y: "-100%" })

    const observer = new IntersectionObserver(([entry]) => {
      gsap.killTweensOf(stickyNav)

      if (!entry.isIntersecting) {
        gsap.to(stickyNav, {
          y: "0%",
          duration: isMobile ? 0 : 0.4,
          ease: "power3.out"
        })
      } else {
        gsap.to(stickyNav, {
          y: "-100%",
          duration: isMobile ? 0 : 0.15,
          ease: "power4.in",
          overwrite: true
        })
      }
    },
    { threshold: 0 }
  )

  const handleResize = () => {
    isMobile = window.innerWidth <= 768
  }

  handleResize()
  window.addEventListener("resize", handleResize)
  observer.observe(sentinel)

  return () => {
    window.removeEventListener("resize", handleResize)
    observer.disconnect()
  }
}, [])

  const handleMouseMove = (e: React.MouseEvent<HTMLDivElement>) => {
    const rect = containerRef.current?.getBoundingClientRect()
    if (!rect) return

    const cx = rect.left + rect.width / 2
    const cy = rect.top + rect.height / 2

    const dx = (e.clientX - cx) / (rect.width / 2)
    const dy = (e.clientY - cy) / (rect.height / 2)

    layerRefs.current.forEach((el, i) => {
      if (!el) return

      const factor = (i / LAYERS.length * 2) * MAX_MOVE

      gsap.to(el, {
        x: dx * factor,
        y: dy * factor * 0.4 / 10,
        duration: 0.6,
        ease: "power2.out",
      })
    })
  }

  const handleMouseLeave = () => {
    layerRefs.current.forEach((el) => {
      if (!el) return
      gsap.to(el, {
        x: 0,
        y: 0,
        duration: 0.8,
        ease: "elastic.out(1, 0.6)",
      })
    })
  }

  return (
    <nav className="relative w-full">
      <div ref={containerRef} onMouseMove={handleMouseMove} onMouseLeave={handleMouseLeave} className="relative w-full aspect-video overflow-hidden max-h-62 max-lg:hidden">
        {LAYERS.map((layer, i) => (
          <div key={layer.src} ref={(el) => { layerRefs.current[i] = el }} className="absolute inset-0 will-change-transform">
            <Image src={layer.src} alt={layer.alt} fill draggable={false} className="object-cover" priority />
          </div>
        ))}

        <div className="absolute inset-0 flex items-center justify-center z-10 -translate-y-3">
          <Image src="/images/logo.png" alt="logo" height={80} width={520} draggable={false} className="object-contain" priority />
        </div>

        <div className="absolute bottom-0 left-0 right-0 z-20 bg-slate-950/50 text-white text-center text-[21px] flex items-center justify-center gap-7">
          <button onClick={() => copyText("us.mineplex.com")} className="text-amber-500 cursor-pointer transition-colors duration-300 hover:text-amber-300">
            US.MINEPLEX.COM
          </button>

          <h1 className="text-amber-300">
            10000 PLAYERS ONLINE
          </h1>

          <button onClick={() => copyText("pe.mineplex.com")} className="text-amber-500 cursor-pointer transition-colors duration-300 hover:text-amber-300">
            PE.MINEPLEX.COM
          </button>
        </div>
      </div>

      <div ref={sentinelRef} className="h-0" />

      <div className="navbar">
        <Link href={ROUTES.HOME} className="link-icon lg:hidden">
          <Image src="/icons/icon.png" alt="Mineplex" height={48} width={48} />
        </Link>
        
        <div className="flex items-center justify-center gap-12 max-md:hidden">
          {navLinks.map((link, idx) => (
            <Link href={link.route} key={idx} className={`${pathname === link.route && "text-amber-400"} transition-colors duration-200 hover:text-amber-400`}>
              {link.label}
            </Link>
          ))}
        </div>

        <div className="flex items-center gap-2 lg:hidden">
          <div className="md:hidden">
            <NavbarMobile />
          </div>

          <Link href={ROUTES.PLAY} className="link-primary bg-amber-600 text-[15px] hover:bg-amber-700">
            Play Now!
          </Link>
        </div>
      </div>

      <div ref={stickyNavRef} className="navbar fixed top-0 left-0 right-0 z-50 justify-between">
        <Link href={ROUTES.HOME} className="link-icon">
          <Image src="/icons/icon.png" alt="Mineplex" height={48} width={48} />
        </Link>

        <div className="flex items-center gap-12 max-md:hidden">
          {navLinks.map((link, idx) => (
            <Link href={link.route} key={idx} className={`${pathname === link.route && "text-amber-400"} transition-colors duration-200 hover:text-amber-400`}>
              {link.label}
            </Link>
          ))}
        </div>

        <div className="flex items-center gap-2">
          <div className="md:hidden">
            <NavbarMobile />
          </div>

          <Link href={ROUTES.PLAY} className="link-primary bg-amber-600 text-[15px] hover:bg-amber-700">
            Play Now!
          </Link>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
