import React, { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate, Link } from "react-router-dom"; 
import "../styles/login.css"; 
import toast from "react-hot-toast";

export default function Login() {
  const { login } = useContext(AuthContext);
  const nav = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  async function submit(e) {
    e.preventDefault();
    try {
      await login(username, password);
      toast.success("Logged in successfully!");
      nav("/messages");
    } catch (err) {
       toast.error("Invalid username or password.");
    }
  }

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={submit}>
        <h2>Login</h2>

        <input
          value={username}
          onChange={e => setUsername(e.target.value)}
          placeholder="Username"
        />

        <input
          type="password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          placeholder="Password"
        />

        <button type="submit">Sign In</button>

        <p className="register-link">
          Don't have an account? <Link to="/signup">Sign up</Link>
        </p>
      </form>
    </div>
  );
}
