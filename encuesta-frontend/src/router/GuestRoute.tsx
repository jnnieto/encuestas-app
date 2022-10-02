import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

const GuestRoute = () => {
  const user = useAuth();
  return (
    !user.isAuthenticated ? <Outlet /> : <Navigate to="/user" />
  )
}

export default GuestRoute