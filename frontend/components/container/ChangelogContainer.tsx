import Link from "next/link"
import PageContainer from "../page/PageContainer"
import { ROUTES } from "@/constants/routes"
import { UnixUtil } from "@/utils/UnixUtil"

interface Props {
  id: number,
  title: string,
  description: string[],
  createdBy: string,
  createdAt: number
}

const ChangelogContainer = ({ id, title, description, createdBy, createdAt }: Props) => {
  return (
    <PageContainer title={title} childrenClassName="space-y-8">
      <div className="text-[20px] font-roboto font-light space-y-3">
        {description.map((line, idx) => (
          <p key={idx}>
            {line}
          </p>
        ))}
      </div>

      <div className="space-y-4">
        <div className="flex items-center gap-3">
          <p className="text-neutral-400 text-[13px] uppercase">
            {UnixUtil.formatDate(createdAt)}
          </p>

          <div className="bg-linear-to-r from-neutral-200 to-transparent h-1 w-full" />
        </div>

        <div className="flex max-sm:flex-col items-start justify-between gap-y-4">
          <h1 className="text-[18px] font-light">
            Posted by <span className="font-normal">{createdBy}</span>
          </h1>

          <Link href={`${ROUTES.CHANGELOG}/${id}`} className="bg-amber-500 border-b-3 border-b-amber-900 py-2 px-8 max-sm:w-full text-center text-white font-oswald text-shadow-sm/20 rounded-t-sm rounded-b-lg transition-colors duration-200 hover:bg-amber-600">
            View More
          </Link>
        </div>
      </div>
    </PageContainer>
  )
}

export default ChangelogContainer
