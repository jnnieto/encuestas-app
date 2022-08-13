import {
  BrowserRouter as Router,
  Route,
  Link,
  Routes
} from "react-router-dom"
import AuthProvider from "./context/authContext"
import Home from "./pages/Home"
import Login from "./pages/Login"
import Register from "./pages/Register"

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Home />}/>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />}/>
        </Routes>
      </Router>
    </AuthProvider>
  )
}

export default App
