import { Link } from "react-router-dom";
import "../css/Navbar.css";
import logo from "../assets/logo.svg";
import GoogleSignIn from "./GoogleSignIn";

function Navbar() {
  return (
    <div className="navbar">
      <div className="navbar-items">
        <Link to={"/"} className="nav-text nav-link">
          <img src={logo} alt="logo" className="logo" />
        </Link>
        <Link to={"/dashboard"} className="nav-text nav-link">
          <GoogleSignIn />
        </Link>
      </div>
    </div>
  )
}

export default Navbar;