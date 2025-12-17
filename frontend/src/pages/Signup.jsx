import React, { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate, Link } from "react-router-dom";
import "../styles/signup.css";
import toast from "react-hot-toast";

export default function Signup() {
  const { signup } = useContext(AuthContext);
  const nav = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  async function submit(e) {
    e.preventDefault();

  try {
      await signup(username, password);
      toast.success("Account created!");
      nav("/login");
    } catch (err) {
      if (err.response?.status === 409) {
        toast.error(err.response.data.error || "Username already exists");
      } else {
        toast.error("Signup failed.");
      }
    }
  }


  return (
    <div className="signup-container">
      <form className="signup-form" onSubmit={submit}>
        <h2>Create Account</h2>

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

        <button type="submit">Sign Up</button>

        <p className="login-link">
          Already have an account?{" "}
          <Link to="/login">Login</Link>
        </p>
      </form>
    </div>
  );
}
