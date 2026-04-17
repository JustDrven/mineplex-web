import StaffMemberCard from "@/components/card/StaffMemberCard";
import PageContainer from "@/components/page/PageContainer";
import { Rank } from "@/enums/Rank";
import { RankUtil } from "@/utils/RankUtil";

export const metadata = {
  title: "Staff Team",
};

const Page = () => {
  return (
    <PageContainer title="Staff Team" childrenClassName="space-y-8">
      <p>
        The complete list of all of the Mineplex Staff Team members.
      </p>

      <section className="space-y-12">
        <div className="space-y-4">
          <div className="flex items-center gap-3">
            <p className="text-neutral-400 text-[13px] whitespace-nowrap uppercase">
              {RankUtil.getRankLabel[Rank.Owner]}
            </p>

            <div className="bg-linear-to-r from-neutral-200 to-transparent h-1 w-full" />
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
            <StaffMemberCard uuid="38c536ca-4f10-4e6e-b8b2-4536943bc315" nickname="James" username="james" />
            <StaffMemberCard uuid="bcf3bef5-dfde-4798-9c3d-88cf23122ae2" nickname="Flaymed" username="flaymed" />
            <StaffMemberCard uuid="07c918bc-dcac-4fd1-8a2e-a15d335d4c45" nickname="Oro" username="oro" />
          </div>
        </div>

        <div className="space-y-4">
          <div className="flex items-center gap-3">
            <p className="text-neutral-400 text-[13px] whitespace-nowrap uppercase">
              {RankUtil.getRankLabel[Rank.Leader]}
            </p>

            <div className="bg-linear-to-r from-neutral-200 to-transparent h-1 w-full" />
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
            <StaffMemberCard uuid="38c536ca-4f10-4e6e-b8b2-4536943bc315" nickname="James" username="james" />
          </div>
        </div>
      </section>
    </PageContainer>
  )
}

export default Page
