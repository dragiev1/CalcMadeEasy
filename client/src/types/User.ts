export interface User {
  id: string;
  googleId: string;
  firstName: string;
  lastName: string;
  email: string;
  profilePicUrl: string;
  coursesEnrolled?: any[] // TODO: Make course enrollment interface later.
  createdAt?: string;
  updatedAt?: string;
}

export interface UserProps {
  firstName: string;
  lastName: string;
  email: string;
  profilePic: string;
  createdAt: string;
}

export interface UserProgressProps {
  id: string;
  userId: string;
  problemId: string;
  pageId: string;

  answer: string;
  isCorrect: boolean;
  points: number;
  attempts?: number;
  pointsEarned?: number;
  lastAttempted?: string;

  createdAt: string;
  updatedAt: string;
}