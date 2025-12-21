import { Navigate } from "react-router-dom";
import { useAuth } from "../auth/authContext";

const ProtectedRoute = ({ children, role }) => {
  const { user, token } = useAuth();

  // Main auth check using token
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // Role-based access
  if (role && user?.role !== role) {
    return <h3>Access Denied</h3>;
  }

  return children;
};

export default ProtectedRoute;
