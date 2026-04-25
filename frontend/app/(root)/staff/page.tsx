import StaffMemberCard from "@/components/card/StaffMemberCard";
import PageContainer from "@/components/page/PageContainer";
import { loadStaff, StaffCategory } from "@/lib/staff";

const Page = async () => {
  const staffTeam: StaffCategory[] = await loadStaff();

  const isEmpty = staffTeam.length === 0;

  return (
    <PageContainer title="Staff Team" childrenClassName="space-y-8">
      <p>
        {isEmpty ? "Something went wrong..." : "The complete list of all of the Mineplex Staff Team members."}
      </p>

      {!isEmpty && (
        <section className="space-y-10">
          {staffTeam.map((c, idx) => (
            <div key={idx} className="space-y-4">
              <div className="flex items-center gap-3">
                <p className="text-neutral-400 text-[13px] whitespace-nowrap uppercase">
                  {c.name}
                </p>

                <div className="bg-linear-to-r from-neutral-200 to-transparent h-1 w-full" />
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
                {c.users.map((user, index) => {
                  return <StaffMemberCard key={index} nickname={user.name} rank={user.rank.display} />
                })}
              </div>
            </div>
          ))}
        </section>
      )}
    </PageContainer>
  )
}

export default Page
