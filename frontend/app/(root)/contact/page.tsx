import PageContainer from "@/components/page/PageContainer";
import { contactList } from "@/constants/contact";
import Link from "next/link";

export const metadata = {
  title: "Contact",
};

const Page = () => {
  return (
    <PageContainer title="Contact Mineplex" childrenClassName="text-[20px] font-roboto font-light space-y-8">
      {contactList.map((c, idx) => (
        <div className="space-y-2" key={idx}>
          <b className="block font-medium">
            {c.title}:
          </b>

          <ul className="ml-7 list-disc">
            {c.contact.map((contact, idz) => (
              <li key={idz}>
                <div className="flex items-center gap-1.5">
                  {contact.label && (
                    <p className="font-normal">
                      {contact.label}:
                    </p>
                  )}

                  <Link href={contact.link.href} target="_blank" className="text-amber-600 link-tertiary hover:text-amber-700">
                    {contact.link.label}
                  </Link>
                </div>
              </li>
            ))}
          </ul>
        </div>
      ))}
    </PageContainer>
  )
}

export default Page
