import axios from "axios"
import { Login } from "../interfaces/login";
import { UserResgister } from "../interfaces/user-register"
import { LOGIN_ENDPOINT, REGISTER_ENDPOINT } from "../utils/enpoints";

export const register = (newUser: UserResgister) => {
    return axios.post(REGISTER_ENDPOINT, newUser);
} 

export const login = (login: Login) => {
    return axios.post(LOGIN_ENDPOINT, login);
}