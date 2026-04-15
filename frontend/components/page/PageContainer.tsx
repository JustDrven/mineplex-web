import React from "react"

interface Props {
  title?: string,
  className?: string,
  childrenClassName?: string,
  children: React.ReactNode
}

const PageContainer = ({ title, className, childrenClassName, children }: Props) => {
  return (
    <div className={`${className} bg-white py-6 px-9 mt-8 mx-3 sm:mx-[2vh] md:mx-[12vh] lg:mx-[16vh] xl:mx-[22vh] 2xl:mx-[48vh] rounded-t-md rounded-b-xl drop-shadow-md/20 space-y-8`}>
      {title && (
        <section>
          <h1 className="text-neutral-700 text-[36px] font-black uppercase">
            {title}
          </h1>

          <div className="bg-linear-to-r from-amber-500 to-transparent h-2" />
        </section>
      )}

      <section className={`${childrenClassName} text-[20px] font-roboto font-light`}>
        {children}
      </section>
    </div>
  )
}

export default PageContainer
