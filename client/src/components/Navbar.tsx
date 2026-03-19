import { Link } from "react-router-dom";
import "../css/Navbar.css";

function Navbar() {
  return (
    <div className="navbar">
      <div className="navbar-items">
        <Link to={"/"} className="nav-text nav-link">Icon</Link>
        <Link to={"/dashboard"} className="nav-text nav-link">Learn</Link>
      </div>
    </div>
  )
}

export default Navbar;