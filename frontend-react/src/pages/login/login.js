import { useState } from "react";
import api from "../../api/axios";
import { useAuth } from "../../auth/authContext";
import { useNavigate } from "react-router-dom";
import "./login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async () => {
    setError("");

    // Simple frontend validation
    if (!email || !password) {
      setError("Please enter both email and password");
      return;
    }

    try {
      const res = await api.post("/api/v1/auth/login", { email, password });

      // Backend success check
      if (!res.data.success) {
        setError(res.data.message || "Login failed");
        return;
      }

      const token = res.data.data.token;
      const role = res.data.data.role;
      const fullName = res.data.data.fullName;

      if (!token || !role) {
        setError("Login failed. Please try again.");
        return;
      }

      // Save in context / localStorage
      login(token, { role, fullName });

      // Redirect immediately based on role
      if (role === "ADMIN") navigate("/admin");
      else navigate("/chat");

    } catch (err) {
      setError(err.response?.data?.message || "Login failed. Check credentials");
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>IntraWise Login</h2>

        <input
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleLogin}>Login</button>

        {error && <p className="error-message">{error}</p>}
      </div>
    </div>
  );
};

export default Login;
