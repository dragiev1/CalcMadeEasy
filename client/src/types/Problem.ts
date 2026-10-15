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

// 2. Update ProblemProps to use the Tag array
export interface ProblemProps {
  question: string;
  position: number;
  confidenceScore?: number;
  showQuestion?: boolean;
  solved?: boolean;
  tags?: Tag[]; 
  userSolution?: {
    answer: string;
    isCorrect?: boolean;
  };
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

