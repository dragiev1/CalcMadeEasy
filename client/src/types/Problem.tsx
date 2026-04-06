import type { TagProps } from "./Tag";

export interface ProblemProps {
  question: React.ReactNode;
  confidenceScore?: number;
  showQuestion?: boolean;
  solved: boolean;
  tags?: TagProps[]
}