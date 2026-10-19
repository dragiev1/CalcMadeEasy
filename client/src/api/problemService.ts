import type { Problem } from "../types/Problem";

// const CURRENT_USER_EMAIL = "dragievs2020@gmail.com";

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1/problems';


export const getProblemById = async (id: string): Promise<Problem> => {
  const response = await fetch(`${API_BASE_URL}/${id}`);
  if (!response.ok) throw new Error(`Failed to fetch problem: ${response.status} ${response.statusText}`);
  return response.json();
}

export const verifyUserSolution = async (id: string, solution: string): Promise<{isCorrect: boolean, attempts: number; lastAttempted:string; pointsEarned: number}> => {
  const response = await fetch(`${API_BASE_URL}/${id}/verify`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      // Uncomment when we need to authorize the user sending the request later.
      // 'Authorization': 'Bearer ${localStorage.getItem('jwt_token')}'
    },
    body: JSON.stringify({ solution }),
  })

  if(!response.ok) {
    const errorText = await response.text();
    throw new Error(`Verification failed: ${response.status} - ${errorText}`);
  }

  return response.json();
}