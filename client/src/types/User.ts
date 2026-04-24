export interface UserProps {
  firstName: string;
  lastName: string;
  email: string;
  profilePic: string;
  // courses: Course[];
  // grades: UserProgress[];
  createdAt: Date;
}

export interface UserProgressProps {
  id: number;
  userId: number;
  problemId: number;
  pageId: number;

  answer: string;
  isCorrect: boolean;
  points: number;
  attempts?: number;
  pointsEarned?: number;
  lastAttempted?: number;

  createdAt: number;
  updatedAt: number;
}

