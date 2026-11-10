import type { Problem } from "../types/Problem";
import type { UserProgressResponse } from "../types/User";
import { apiClient } from "./apiClient";

export const getProblemById = (id: string) =>
  apiClient<Problem>(`/problems/${id}`, { method: 'GET' });

export const verifyUserSolution = (pageId:string, problemId: string, solution: string) => {
  return apiClient<{
    correct: boolean,
    attempts: number,
    lastAttempted: string,
    pointsEarned: number
    maxAttemptsReached: boolean,
  }>
  (`/users/page/${pageId}/problems/${problemId}/verify`, {
    method: 'POST',
    body: JSON.stringify({ userSolution: solution })
  });
}

export const getUserProblemProgress = async (userId: string, problemId: string, pageId: string) => {
  return apiClient<UserProgressResponse>(
    `/users/${userId}/${problemId}/${pageId}`, {
      method: 'GET'
    }
  ) 
}