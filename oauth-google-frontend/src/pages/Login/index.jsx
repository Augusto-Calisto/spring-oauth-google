// React 
import { useEffect } from "react";

// Hooks React Router Dom
import { useLocation, useNavigate } from "react-router-dom";

// AuthContext
import { useAuth } from "../../context/AuthContext";

// Styles
import "./style.css";

const Login = () => {
	const { user, isAuthenticated } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();

    const fromLocation = (location.state)?.from?.pathname || "/";

    useEffect(() => {
        if(isAuthenticated && user) {
            navigate(fromLocation, { replace: true })
        }
    }, [isAuthenticated, fromLocation, navigate]);
    
    const handleLogin = (provider) => {
        if(provider === "google") {
            window.location.href = "http://localhost:8080/oauth2/authorization/google";
        }

        return ;
    }

	return (
		<div className="container">
			<div id="container-logo">
				<a>
					<img src="/img/vite.svg" className="logo" alt="Vite logo"/>
				</a>
				
				<a>
					<img src="/img/react-logo.png" className="logo react" alt="React logo"/>
				</a>
			</div>

			<h1> Google Authentication </h1>

			<div className="card">
				<button onClick={() => handleLogin("google")} className="btn-google">
					<img src="/img/logo-google.png" alt="Google"/>
					Sign in with Google
				</button>
			</div>

			<p className="read-the-docs">
				Click on the Vite and React logos to learn more
			</p>
		</div>
	)
}

export default Login;