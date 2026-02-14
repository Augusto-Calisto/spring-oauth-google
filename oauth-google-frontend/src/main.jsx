// React
import { createRoot } from "react-dom/client";

// React Router Dom
import { BrowserRouter, Route, Routes } from "react-router-dom";

// Context
import { AuthProvider } from "./context/AuthContext.jsx";

// Components
import Login from "./pages/Login/index.jsx";
import Home from "./pages/Home/index.jsx";

// Security Component
import ProtectedRoute from "./security/ProtectedRoute.jsx";

// Styles
import "./index.css";

createRoot(document.getElementById("root")).render(
	<AuthProvider>
		<BrowserRouter>
			<Routes>
				{ /* Rotas publicas */ }
				<Route path="/login" element={ <Login/> } />

				{ /* Rotas privadas para ADMIN e USER */ }
				<Route element={ <ProtectedRoute/> }>
					<Route path="/" element={ <Home/> } />
				</Route>
			</Routes>
		</BrowserRouter>
	</AuthProvider>
);