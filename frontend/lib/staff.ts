const STAFF_URL = process.env.NEXT_PUBLIC_STAFF_SERVICE || "https://staff.mc-svc.mineplex.com/staff"

interface StaffCategory {
    name: string
    users: StaffUser[]
}

interface StaffUser {
    name: string
    rank: StaffUserRank
}

interface StaffUserRank {
    display: string
    priority: number
}

const loadStaff = async (): Promise<StaffCategory[]> => {
    const response = await fetch(STAFF_URL);

    if (!response.ok) {
        throw new Error(`Failed to load staff: ${response.status} ${response.statusText}`);
    }

    const result = await response.json();
    return result as StaffCategory[];
}

export type { StaffCategory, StaffUser, StaffUserRank };
export { loadStaff };