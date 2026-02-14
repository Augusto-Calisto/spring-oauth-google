// Auth Context
import { useAuth } from "../../context/AuthContext";

// Styles
import "./style.css";

const Home = () => {
    const { user, setUser, setIsAuthenticated } = useAuth();

    const handleLogout = () => {
        setIsAuthenticated(false);
        setUser(null);
        window.location.href = "http://localhost:8080/logout";
    }

    return (
        <div className="container-home">
            <div className="container-welcome">
                <img src={user?.urlPicture} alt="Imagem Perfil do Usuario"/>
                <h3> Welcome, { user?.name } </h3>
            </div>

            <div className="container-info">
                <textarea cols={170} rows={20}>
                    { JSON.stringify(user) }
                </textarea>

                <button onClick={handleLogout}> Logout </button>
            </div>
        </div>
    )
}

export default Home;