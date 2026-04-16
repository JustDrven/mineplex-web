import { Rank } from "@/enums/Rank";

export class RankUtil {
  static getRankLabel: { [rank in Rank]: string } = {
    [Rank.Owner]: "Owner",
    [Rank.Leader]: "Team Leader",
    [Rank.Admin]: "Admin",
    [Rank.Developer]: "Development",
    [Rank.Support]: "Support",
    [Rank.ClansManager]: "Clans Management",
    [Rank.CommunityManager]: "Community Management",
    [Rank.ForumManager]: "Forum Management",
    [Rank.StaffManager]: "Staff Management",
    [Rank.Recruiter]: "Recruitment",
    [Rank.EventSquad]: "Event Squad",
    [Rank.QA]: "Quality Assurance",
    [Rank.SocialMedia]: "Social Media",
    [Rank.Mod]: "Mod",
    [Rank.Trainee]: "Trainee",
    [Rank.BuildLead]: "Build Lead",
    [Rank.BuildTeam]: "Build Team",
  };

  static getRankColor: { [rank in Rank]: string } = {
    [Rank.Owner]: "red-500",
    [Rank.Leader]: "red-500",
    [Rank.Admin]: "red-500",
    [Rank.Developer]: "red-500",
    [Rank.Support]: "green-500",
    [Rank.ClansManager]: "sky-500",
    [Rank.CommunityManager]: "amber-500",
    [Rank.ForumManager]: "amber-500",
    [Rank.StaffManager]: "amber-500",
    [Rank.Recruiter]: "amber-400",
    [Rank.EventSquad]: "pink-500",
    [Rank.QA]: "red-500",
    [Rank.SocialMedia]: "fuchsia-500",
    [Rank.Mod]: "amber-500",
    [Rank.Trainee]: "amber-400",
    [Rank.BuildLead]: "red-500",
    [Rank.BuildTeam]: "sky-500",
  };
}
