// React
import { createRoot } from "react-dom/client";

// React Router Dom
import { BrowserRouter, Route, Routes } from "react-router-dom";

// Context
import { AuthProvider } from "./context/AuthContext.jsx";

// Components
import Login from "./pages/Login/index.jsx";

// Styles
import "./index.css";

createRoot(document.getElementById("root")).render(
	<AuthProvider>
		<BrowserRouter>
			<Routes>
				<Route path="/" element={ <Login/> } />
			</Routes>
		</BrowserRouter>
	</AuthProvider>
);