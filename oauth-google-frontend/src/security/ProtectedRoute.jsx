// React Router Dom
import { Navigate, Outlet } from "react-router-dom";

// Auth Context
import { useAuth } from "../context/AuthContext";

const ProtectedRoute = ({ allowedRoles }) => {
    const { user, isAuthenticated, loading } = useAuth();

    if(loading) {
        return <div> Loading... </div>;
    }

    // 1. Verifica se o usuário não está logado
    if(!user || !isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    // 2. Caso não passe regra de segurança será acessivel para ADMIN e USER
    if(!allowedRoles || allowedRoles.length === 0) {
        return <Outlet/>;
    }

    // 3. Verifica se a role do usuário está na lista de permissões
    const hasAccess = allowedRoles.includes(user?.role);

    return hasAccess ? <Outlet/> : <Navigate to="/forbidden" replace />;
};

export default ProtectedRoute;