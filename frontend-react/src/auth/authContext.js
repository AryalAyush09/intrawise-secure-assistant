import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  // Safe initialization from localStorage
  const storedUser = localStorage.getItem("user");
  const [user, setUser] = useState(
    storedUser && storedUser !== "undefined" ? JSON.parse(storedUser) : null
  );

  const [token, setToken] = useState(localStorage.getItem("token") || null);

  const login = (token, user) => {
    localStorage.setItem("token", token);
    if (user) {
      localStorage.setItem("user", JSON.stringify(user));
      setUser(user);
    } else {
      localStorage.removeItem("user");
      setUser(null);
    }
    setToken(token);
  };

  const logout = () => {
    localStorage.clear(); // removes token + user safely
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
