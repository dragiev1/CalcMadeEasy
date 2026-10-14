import axios from "axios";
import type { Problem } from "../types/Problem";

// const CURRENT_USER_EMAIL = "dragievs2020@gmail.com";

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1/problems';


export const getProblemById = async (id: string): Promise<Problem> => {
  const response = await axios.get<Problem>(`${API_BASE_URL}/${id}`);
  return response.data;
}