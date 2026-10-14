import axios from "axios";

// Move to .env later. 
const API_BASE_URL = 'http://localhost:8080/api/auth';

// Define shape of the data we expect back.
export interface AuthResponse {
    token: string;
    user: {
        id: string;
        firstName: string;
        lastName: string;
        email: string;
        profilePicUrl: string;
    };
}

// TODO: REMOVE AXIOS ASAP
// Dedicated function for logging in.
export const loginWithGoogle = async (idToken: string): Promise<AuthResponse> => {
    const response = await axios.post<AuthResponse>(`${API_BASE_URL}/google`, {
        idToken: idToken
    });
    return response.data;
}

export const logout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
};