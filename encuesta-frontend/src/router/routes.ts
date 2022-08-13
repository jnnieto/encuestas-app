import Home from "../pages/Home";
import Register from "../pages/Login";
import Login from "../pages/Register";
import User from "../pages/User";
import { Route } from "../types";

const routes: Route[] = [
    {
        path: "/",
        component: Home,
        routeType: "PUBLIC"
    },
    {
        path: "/login",
        component: Login,
        routeType: "GUEST"
    },
    {
        path: "/register",
        component: Register,
        routeType: "GUEST"
    },
    {
        path: "/user",
        component: User,
        routeType: "PRIVATE"
    },
];

export default routes;