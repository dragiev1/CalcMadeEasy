import type { TagProps } from "./Tag";

export interface ProblemProps {
  question: React.ReactNode;
  position?: number;
  confidenceScore?: number;
  showQuestion?: boolean;
  solved: boolean;
  tags?: TagProps[]
}