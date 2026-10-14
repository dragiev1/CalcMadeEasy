import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { type User } from '../types/User';
import { logout } from '../services/authService';

export default function Dashboard() {
  const [user, setUser] = useState<User | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    const token = localStorage.getItem('authToken');

    // Extra security; if no user or token, kick them back to login.
    if(!storedUser || !token) {
        navigate('/');
        return;
    }

    // Treat the parse JSON as User object.
    setUser(JSON.parse(storedUser) as User);
  }, [navigate]);

  if(!user) {
    return <p>Loading your profile...</p>
  }

  const handleLogOut = () => {
    logout();
    navigate('/');
  };

  return (
    <div style={{padding: '50px', textAlign: 'center'}}>
        <h1 style={{color: '#4CAF50'}}>SUCCESS</h1>
        <h2>Welcome, {user.firstName} {user.lastName}!</h2>

        <img src={user.profilePicUrl} alt="Profile" style={{
            borderRadius: '50%',
            width: '120px',
            height: '120px',
            border: '4px solid #4CAF50',
            marginTop: '20px'
        }} />

        <p style={{fontSize: '1.2rem', marginTop: '20px'}}>
            <strong>Email:</strong> {user.email}
        </p>

        <p style={{color: '#666'}}>
            <strong>Courses Enrolled:</strong> {user.coursesEnrolled?.length || 0}
        </p>

        <button onClick={handleLogOut} style={{
            marginTop: '30px',
            padding: '10px 20px',
            backgroundColor: '#f44336',
            color: 'white',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer'
        }}>Logout</button>

    </div>
  )
}