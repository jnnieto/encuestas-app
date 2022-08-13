import { createContext, Dispatch, FC, ReactNode, useReducer } from "react";
import { AuthActions } from "../state/actions/authActions";
import { authInitialState, AuthReducer } from "../state/reducers/authReducer";
import { User } from "../types";

const AuthStateContext = createContext<User>(authInitialState);

const AuthDispatchContext = createContext<Dispatch<AuthActions>>(() => undefined);

interface AuthProviderProps {
    children: ReactNode
}

const AuthProvider:FC<AuthProviderProps> = ({ children }) => {
  const [user, dispatch] = useReducer(AuthReducer, authInitialState);
  return (
      <AuthStateContext.Provider value={user}>
        <AuthDispatchContext.Provider value={dispatch}>
            {children}
        </AuthDispatchContext.Provider>
      </AuthStateContext.Provider>
  )
}

export {
    AuthStateContext,
    AuthDispatchContext
}

export default AuthProvider;