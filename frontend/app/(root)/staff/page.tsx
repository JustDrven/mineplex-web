'use client'

import StaffMemberCard from "@/components/card/StaffMemberCard";
import PageContainer from "@/components/page/PageContainer";
import { loadStaff, StaffCategory } from "@/lib/staff";
import { useEffect, useState } from "react";

const Page = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [staffTeam, setStaffTeam] = useState<StaffCategory[]>([]);

  useEffect(() => {
    loadStaff().then(result => {
      setStaffTeam(result);
      setLoading(false);

    }).catch((err) => console.log(err));
  }, []);

  function renderStaff() {
    if (loading) {
      return <p>Loading..</p>
    }

    return (
      <div>
        {staffTeam.map((category, index) => {
          return (
            <div key={index} className="space-y-4">
              <div className="flex items-center gap-3">
                <p className="text-neutral-400 text-[13px] whitespace-nowrap uppercase">
                  {category.name}
                </p>
                <div className="bg-linear-to-r from-neutral-200 to-transparent h-1 w-full" />
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
                {category.users.map((user, index) => {
                  return <StaffMemberCard key={index} nickname={user.name} rank={user.rank.display} />
                })}
              </div>
              <br />
            </div>
          )
        })}
      </div>
    )
  }

  return (
    <PageContainer title="Staff Team" childrenClassName="space-y-8">
      <p>
        The complete list of all of the Mineplex Staff Team members.
      </p>

      <section className="space-y-12">
        {renderStaff()}
      </section>
    </PageContainer>
  )
}

export default Page
