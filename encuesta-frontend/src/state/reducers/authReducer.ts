import produce from "immer";
import { User } from "../../types";
import { authenticate, logout } from "../../utils/auth";
import { AuthActions } from "../actions/authActions";

export const authInitialState: User = authenticate();

export const AuthReducer = produce((state: User, action: AuthActions): User => {
    switch(action.type) {
        case 'login':
            state = authenticate(action.token);
            return state;
        case 'logout':
            state = logout();
            return state;
        default: 
            return state;
        
    }
});