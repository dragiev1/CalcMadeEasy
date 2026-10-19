import type { TagProps } from "./Tag";

export interface Problem {
  id: string;
  description: string;
  points: number;
  isChallenge: boolean;
  solution: string;
  solutionType: string;  // TOOO: make a solutiontype enum.
  tags?: TagProps[];
  createdAt?: string;
  updatedAt?: string;
}

export interface Tag {
  id: string;
  name: string;
  difficulty: number;
}

export interface ProblemProps {
  id: string;
  description: string;
  position: number;
  confidenceScore?: number;
  showQuestion?: boolean;
  solved?: boolean;
  tags?: Tag[]; 
  userSolution?: string;
}

export interface ProblemResponse {
  id: string;
  description: string;
  points: number;
  isChallenge: boolean;
  solution: string;
  solutionType: string;  // TODO: make a solutiontype interface enum.
}

export interface UpdateProblem {
  description: string;
  points: number;
  isChallenge: boolean;
  solution: string;
  solutionType: string;   // TODO: make a solutiontype interface enum.
}

export interface AnswerInputProps {
  value: string;
  onChange: (value: string) => void;
  onSubmit: () => void;
  placeholder?: string;
  disabled?: boolean;
  className?: string;
  "aria-label"?: string;
  onLaTeXMenuOpen?: () => void;
  evaluationStatus?: "idle" | "checking" | "correct" | "incorrect";
}