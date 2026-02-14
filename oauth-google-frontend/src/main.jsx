// React
import { createRoot } from "react-dom/client";

// React Router Dom
import { BrowserRouter, Route, Routes } from "react-router-dom";

// Context
import { AuthProvider } from "./context/AuthContext.jsx";

// Components
import Login from "./pages/Login/index.jsx";
import Home from "./pages/Home/index.jsx";
import Forbidden from "./pages/Forbidden/index.jsx";
import Admin from "./pages/Admin/index.jsx";

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
				<Route path="/forbidden" element={ <Forbidden/> } />

				{ /* Rotas privadas. Consegue acessar estando autenticado */ }
				<Route element={ <ProtectedRoute/> }>
					<Route path="/" element={ <Home/> } />
				</Route>

				{ /* Rotas privadas apenas para ADMIN */ }
				<Route element={ <ProtectedRoute allowedRoles={['ADMIN']}/> }>
					<Route path="/admin/dashboard" element={ <Admin/> }/>
				</Route>
			</Routes>
		</BrowserRouter>
	</AuthProvider>
);