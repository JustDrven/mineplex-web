import { ROUTES } from "@/constants/routes"
import Image from "next/image"
import Link from "next/link"

interface Props {
  nickname: string,
  rank: string,
}

const StaffMemberCard = ({ nickname, rank }: Props) => {
  return (
    <Link href={`${ROUTES.FORUMS_USER}/${nickname}`} className="bg-neutral-100 py-5 px-5 rounded-t-md rounded-b-xl drop-shadow-sm/30 flex flex-col items-center text-center transition-all duration-200 hover:drop-shadow-lg/30">
      <div className="space-y-2">
        <Image src={`https://visage.surgeplay.com/bust/128/${nickname}`} alt={nickname} unoptimized draggable={false} height={92} width={92} />

        <h1 className="font-normal">
          {nickname}
        </h1>
        <p>{rank}</p>
      </div>
    </Link>
  )
}

export default StaffMemberCard
