export interface UserProps {
  firstName: string;
  lastName: string;
  email: string;
  profilePic: string;
  // courses: Course[];
  createdAt: Date;
}

export interface UserProgress {
  id: number;
  userId: number;
  problemId: number;
  pageId: number;

  isChallenge: boolean;
  points: number;
  attempts?: number;
  pointsEarned?: number;
  lastAttempted: Date;

  createdAt: Date;
  updatedAt: Date;
}

