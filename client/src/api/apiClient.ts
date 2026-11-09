const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1';


// Reusable function to authorize API calls. 
export async function apiClient<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    const token = localStorage.getItem("authToken");

    const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` }),
            ...options.headers, // Allow overriding headers if needed.
        }

    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: headers,
    })

    // Handle expired jwt causing errors
    if(response.status == 401) {
        localStorage.removeItem('authToken');
        localStorage.removeItem('user');
    }


    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`API Error: ${response.status} ${errorText}`);
    }

    const data = await response.json();
    return data as T;
}