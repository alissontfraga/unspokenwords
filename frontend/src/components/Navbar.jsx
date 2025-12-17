import { Link } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import "../styles/navbar.css"

export default function Navbar() {
  const { user, logout } = useContext(AuthContext);

  return (
    <nav className="navbar">
      <div className="nav-logo">
        <Link to="/">UnspokenWords</Link>
      </div>

      <div className="nav-links">
        {user ? (
            <button className="logout-btn" onClick={logout}>Logout</button>
        ) : (
          <>
            <Link className="login-btn" to="/login">Login</Link>
            <Link className="signup-btn" to="/signup">Sign up</Link>
          </>
        )}
      </div>
    </nav>
  );
}
