import "./css/App.css";
import 'katex/dist/katex.min.css';
import Home from "./pages/Home";
import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />}/>
      <Route path="/login" element={<LoginPage />}/>
      <Route path="/dashboard" element={<Dashboard />}/>
    </Routes>
  )  
}

export default App;
