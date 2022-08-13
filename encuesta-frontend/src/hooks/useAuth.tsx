import { useContext } from "react"
import { AuthDispatchContext, AuthStateContext } from "../context/authContext";

export const useAuth = () => {
    const context = useContext(AuthStateContext);

    if (context === undefined) {
        throw new Error("useAuth must be used with an AuthProvider");
    }

    return context;
}

export const useAuthDispatch = () => {
    const context = useContext(AuthDispatchContext);

    if (context === undefined) {
        throw new Error("useAuthDispatch must be used with an AuthDispatchProvider");
    }

    return context;
}