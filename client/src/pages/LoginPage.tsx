import { GoogleLogin, type CredentialResponse } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import { loginWithGoogle } from "../services/authService";


export default function LoginPage() {
    const navigate = useNavigate();

    const handleSuccess = async (credentialResponse: CredentialResponse) => {
        try {
            // Send Google's token to the backend.
            if (!credentialResponse.credential) {
                console.error("No credential recieved from Google.")
                return;
            }

            // Use service layer to request user + jwt
            const authData = await loginWithGoogle(credentialResponse.credential);

            // Save app's jwt and user data. 
            localStorage.setItem('authToken', authData.token);
            localStorage.setItem('user', JSON.stringify(authData.user))

            navigate('/dashboard');
        } catch (e) {
            console.error("Login failed", e);
            alert("Login failed. Check console.")
        }
    }

    return (
        <div style={{padding: '50px', textAlign: 'center'}}>
            <h1>Welcome to CalcMadeEasy</h1>
            <GoogleLogin 
                onSuccess={handleSuccess}
                onError={() => console.log('Login Failed')}
            />
        </div>
    )
}