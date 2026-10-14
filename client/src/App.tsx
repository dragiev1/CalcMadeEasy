import "./css/App.css";
import 'katex/dist/katex.min.css';
import Home from "./pages/Home";
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />}/>   
    </Routes>
  )  
}

export default App;
