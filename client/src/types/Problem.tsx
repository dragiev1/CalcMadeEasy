import type { TagProps } from "./Tag";
import type { UserProgressProps } from "./User";

export interface ProblemProps {
  id: number,
  question: React.ReactNode;
  position?: number;
  confidenceScore?: number;
  isChallenge?: boolean;
  showQuestion?: boolean;
  solved: boolean;
  tags?: TagProps[]
  userSolution?: UserProgressProps;
}

