import type { Problem } from "../types/Problem";
import { apiClient } from "./apiClient";

export const getProblemById = (id: string) =>
  apiClient<Problem>(`/problems/${id}`, { method: 'GET' });

export const verifyUserSolution = (id: string, solution: string) => {
  return apiClient<{
    isCorrect: boolean,
    attempts: number,
    lastAttempted: string,
    pointsEarned: number
  }>
  (`/problems/${id}/verify`, {
    method: 'POST',
    body: JSON.stringify({ userSolution: solution })
  });
}