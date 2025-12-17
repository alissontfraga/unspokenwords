import React, { createContext, useState, useEffect } from "react";
import { api } from "../services/api";
import { useNavigate } from "react-router-dom";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null); // { username } ou null
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  // tenta validar sessão no carregamento (chama /auth/me se existir)
  useEffect(() => {
    async function check() {
      try {
        const res = await api.get("/auth/me"); 
        setUser(res.data);
      } catch (err) {
        setUser(null);
      } finally {
        setLoading(false);
      }
    }
    check();
  }, []);

  async function login(username, password) {
    // backend deve setar cookie HttpOnly; aqui só tratamos sucesso/erro
    await api.post("/auth/login", { username, password });
    // tenta obter dados do usuário (opcional)
    try {
      const res = await api.get("/auth/me");
      setUser(res.data);
    } catch {
      setUser({ username });
    }
  }

  async function signup(username, password) {
    await api.post("/auth/signup", { username, password });
  }

  async function logout() {
    try {
      await api.post("/auth/logout"); // backend clears cookie
    } catch (err) {
      // ignore
    } finally {
      setUser(null);
      navigate("/login");
    }
  }

  return (
    <AuthContext.Provider value={{ user, loading, login, signup, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
