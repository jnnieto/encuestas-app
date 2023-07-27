import {
  BrowserRouter as Router,
  Route,
  Routes
} from "react-router-dom"
import Navigation from "./components/Navigation"
import AuthProvider from "./context/authContext"
import CreatePoll from "./pages/CreatePoll"
import Home from "./pages/Home"
import Login from "./pages/Login"
import Register from "./pages/Register"
import User from "./pages/User"
import GuestRoute from "./router/GuestRoute"
import PrivateRoute from "./router/PrivateRoute"

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navigation />
          <Routes>
            <Route element={<GuestRoute />}>
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />}/>
            </Route>
            <Route path="/" element={<Home />}/>
            <Route element={<PrivateRoute />}>
              <Route element={<User />} path="/user"/>
              <Route element={<CreatePoll />} path="/create-poll"/>
            </Route>
          </Routes>
      </Router>
    </AuthProvider>
  )
}

export default App
