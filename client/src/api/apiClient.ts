const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1';


// Reusable function to authorize API calls elegantly. 
export async function apiClient<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    const token = localStorage.getItem("authToken");

    // console.log("Token found in localStorage:", token ? "YES (Length: " + token.length + ")" : "NO");

    const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` }),
            ...options.headers, // Allow overriding headers if needed.
        }

    // console.log("Headers being sent to backend:", headers);
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: headers,
    })


    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`API Error: ${response.status} ${errorText}`);
    }

    const data = await response.json();
    return data as T;
}