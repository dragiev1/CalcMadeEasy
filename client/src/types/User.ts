/**
 * ACTUAL INTERFACES:
 */

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

export interface UserProgressResponse {
  attempts: number;
  lastAttempted: string;  // Date.
  pointsEarned: number;
  isCorrect: boolean;
}


/**
 * FOR TESTING / HOMEPAGE PURPOSES ONLY.
 */

export interface UserProps {
  firstName: string;
  lastName: string;
  email: string;
  profilePic: string;
  // courses: Course[];
  // grades: UserProgress[];
  createdAt: Date;
}

