import type { Problem } from "../types/Problem";
import { apiClient } from "./apiClient";

export const getProblemById = (id: string) =>
  apiClient<Problem>(`/problems/${id}`, { method: 'GET' });

export const verifyUserSolution = (pageId:string, problemId: string, solution: string) => {
  return apiClient<{
    isCorrect: boolean,
    attempts: number,
    lastAttempted: string,
    pointsEarned: number
  }>
  (`/users/page/${pageId}/problems/${problemId}/verify`, {
    method: 'POST',
    body: JSON.stringify({ userSolution: solution })
  });
}