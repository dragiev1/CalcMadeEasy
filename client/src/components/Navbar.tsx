import { Link } from "react-router-dom";
import "../css/Navbar.css";
import logo from "../assets/logo.svg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";

function Navbar() {
  return (
    <div className="navbar">
      <div className="navbar-items">
        <Link to={"/"} className="nav-text nav-link">
          <img src={logo} alt="logo" className="logo" />
        </Link>
        <Link to={"/dashboard"} className="nav-text nav-link"><FontAwesomeIcon icon={faGoogle} /></Link>
      </div>
    </div>
  )
}

export default Navbar;