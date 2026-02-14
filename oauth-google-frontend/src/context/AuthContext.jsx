import { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext({
    isAuthenticated: false,
    loading: false,
    user: null,
    setIsAuthenticated: () => {},
    setLoading: () => {},
    setUser: () => {}
})

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [loading, setLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState(null);

    useEffect(() => {
        fetchUser();
    }, []);

    const fetchUser = () => {
        fetch("http://localhost:8080/user/info", { credentials: "include" })
            .then((response) => {
                if(response.ok) {
                    return response.json();
                }
            })
            .then((data) => {
                setUser(data);
                setIsAuthenticated(true);
            })
            .catch((e) => {
                console.log(e.message);
            })
            .finally(() => {
                setLoading(false);
            });
    }

    return (
        <AuthContext.Provider value={{ isAuthenticated, loading, user, setIsAuthenticated, setLoading, setUser }}>
            { children }
        </AuthContext.Provider>
    )
}