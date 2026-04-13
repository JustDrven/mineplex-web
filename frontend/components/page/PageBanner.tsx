import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from "@/components/ui/carousel";
import { bannersList } from "@/constants/banners";
import Image from "next/image";
import Link from "next/link";
import { Fragment } from "react/jsx-runtime";

const PageBanner = () => {
  return (
    <Carousel opts={{ loop: true }} className="border-b-14 border-b-neutral-300">
      <CarouselContent>
        {bannersList.map((banner, idx) => (
          <CarouselItem key={idx} className="relative h-[38vh]">
            <Image src={banner.image} alt={banner.title} fill draggable={false} className="w-full h-[38vh] object-cover" />

            <div className="absolute top-20 left-72 space-y-12 inset-0">
              <div>
                <h1 className="text-white text-[48px] font-bold uppercase drop-shadow-md/80">
                  {banner.title.split(/mineplex/i).map((part, idz, arr) => (
                    <Fragment key={idz}>
                      {part} {
                        idz < arr.length - 1 && (
                          <span className="bg-linear-to-l from-amber-500 to-amber-300 bg-clip-text text-transparent">
                            Mineplex
                          </span>
                        )}
                    </Fragment>
                  ))}
                </h1>

                <p className="text-white text-[28px] font-light drop-shadow-md/80">
                  {banner.description}
                </p>
              </div>

              <Link href={banner.button.route} target={banner.button.target} className="bg-amber-500 border-b-5 border-b-amber-900 py-2 px-12 text-white text-shadow-sm/20 text-[26px] rounded-t-md rounded-b-xl transition-colors duration-200 hover:bg-amber-600">
                {banner.button.label}
              </Link>
            </div>
          </CarouselItem>
        ))}
      </CarouselContent>

      <CarouselNext className="bg-white/50 border-transparent! -translate-x-18 hover:bg-white/75" />
      <CarouselPrevious className="bg-white/50 border-transparent! translate-x-18 hover:bg-white/75" />
    </Carousel>
  )
}

export default PageBanner
