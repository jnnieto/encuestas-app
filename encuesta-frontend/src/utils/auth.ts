import axios from "axios";
import jwt_decode from "jwt-decode";
import { User } from "../types";

const TOKEN_KEY = 'token';
const defaultUser: User = {
    email: "",
    isAuthenticated: false,
    token: ""
}

const setToken = (token: string) => {
    localStorage.setItem(TOKEN_KEY, token);
}

const getToken = () => {
    return localStorage.getItem(TOKEN_KEY) || "";
}

const removeToken = () => {
    return localStorage.removeItem(TOKEN_KEY);
}

export const authenticate = (token?: string): User => {
    if (token) {
        setToken(token);
    }

    const _token = token ? token : getToken();

    if (!token) {
        return {...defaultUser};
    }

    const decoded: any = jwt_decode(_token);
    const currentTime = Date.now() / 1000;

    if (decoded.exp < currentTime) {
        removeToken();
        return {...defaultUser};
    } 

    axios.defaults.headers.common['Authorization'] = _token;

    return {...defaultUser, email: decoded.sub, isAuthenticated: true, token: _token};
}

export const logout = (): User => {
    removeToken();
    // Eliminar token de axios
    delete axios.defaults.headers.common['Authorization'];
    return {...defaultUser};
}