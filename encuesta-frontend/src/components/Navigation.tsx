import { useState } from "react";
import { Link } from "react-router-dom";
import { useAuth, useAuthDispatch } from "../hooks/useAuth"

const Navigation = () => {

  const user = useAuth();
  const authDispatch = useAuthDispatch();

  const [showDropDown, setShowDropDown] = useState<boolean>(false);

  const handleDropDown = () => {
    setShowDropDown(!showDropDown);
  }

  const logout = () => {
    authDispatch({
      type: "logout"
    })
  }

  return (
    <nav className="flex items-center justify-between flex-wrap bg-slate-900 p-6">
      <Link to="/" className="flex items-center flex-shrink-0 text-white mr-6" >
        <svg className="fill-current h-8 w-8 mr-2" width="54" height="54" viewBox="0 0 54 54" xmlns="http://www.w3.org/2000/svg"><path d="M13.5 22.1c1.8-7.2 6.3-10.8 13.5-10.8 10.8 0 12.15 8.1 17.55 9.45 3.6.9 6.75-.45 9.45-4.05-1.8 7.2-6.3 10.8-13.5 10.8-10.8 0-12.15-8.1-17.55-9.45-3.6-.9-6.75.45-9.45 4.05zM0 38.3c1.8-7.2 6.3-10.8 13.5-10.8 10.8 0 12.15 8.1 17.55 9.45 3.6.9 6.75-.45 9.45-4.05-1.8 7.2-6.3 10.8-13.5 10.8-10.8 0-12.15-8.1-17.55-9.45-3.6-.9-6.75.45-9.45 4.05z" /></svg>
        <span className="font-semibold text-xl tracking-tight">Encuestas APP</span>
      </Link>
      {/* <div className="block lg:hidden">
        <button className="flex items-center px-3 py-2 border rounded text-teal-200 border-teal-400 hover:text-white hover:border-white">
          <svg className="fill-current h-3 w-3" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>Menu</title><path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z" /></svg>
        </button>
      </div> */}
      <div className="w-full block flex-grow lg:flex lg:items-center lg:w-auto">
        <div className="text-sm lg:flex-grow">
          <Link to="/" className="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4">
            Inicio
          </Link>

        </div>
        <div className="relative inline-block text-left">
          {user.isAuthenticated ? (
            <>
              <div>
                <button type="button" className="
                inline-flex w-full justify-center rounded-md border border-gray-300
                bg-slate-800 px-4 py-2 text-sm font-medium text-white shadow-sm
                hover:bg-slate-400 focus:outline-none focus:ring-2 focus:ring-indigo-500
                focus:ring-offset-2 focus:ring-offset-gray-100" onClick={handleDropDown} id="menu-button" aria-expanded="true" aria-haspopup="true">
                  { user.email }
                  <svg className="-mr-1 ml-2 h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                    <path fillRule="evenodd" d="M5.23 7.21a.75.75 0 011.06.02L10 11.168l3.71-3.938a.75.75 0 111.08 1.04l-4.25 4.5a.75.75 0 01-1.08 0l-4.25-4.5a.75.75 0 01.02-1.06z" clipRule="evenodd" />
                  </svg>
                </button>
              </div>
              { showDropDown && (
              <div className="absolute right-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-slate-800 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none" role="menu" aria-orientation="vertical" aria-labelledby="menu-button" tabIndex={-1}>
                <div className="py-1" role="none">
                  <Link to="/user" className="text-white hover:text-teal-200 block px-4 py-2 text-sm" role="menuitem" tabIndex={-1} id="menu-item-0">Mis encuestas</Link>
                  <form method="POST" action="#" role="none" onSubmit={logout}>
                    <button type="submit" className="text-white hover:text-teal-200 block w-full px-4 py-2 text-left text-sm" role="menuitem" tabIndex={-1} id="menu-item-3">Cerrar sesión</button>
                  </form>
                </div>
              </div>
              ) }
            </>
          )
            : (
              <>
                <Link to="/login" className="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4">
                  Iniciar sesión
                </Link>
                <Link to="/register" className="block mt-4 lg:inline-block lg:mt-0 text-teal-200 hover:text-white mr-4">
                  Registrarse
                </Link>
              </>
            )
          }
        </div>
      </div>
    </nav>
  )
}

export default Navigation