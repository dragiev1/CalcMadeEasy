import { apiClient } from "./apiClient";
import { type AuthResponse } from "../types/AuthReponse";

// Dedicated function for logging in.
export const loginWithGoogle = async (idToken: string): Promise<AuthResponse> => {
    logout();

    localStorage.clear();
    return apiClient<AuthResponse>(`/auth/google`, {
        method: 'POST',
        body: JSON.stringify({ idToken: idToken })
    });
}

export const logout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
};