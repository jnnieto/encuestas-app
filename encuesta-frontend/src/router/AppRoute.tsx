import { Route, RouteProps, Navigate } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'
import { RouteType } from '../types'

interface AppRouteProps extends RouteProps {
    component: any,
    routeType: RouteType
}

const AppRoute = (props: AppRouteProps) => {
  
    const { component: Component, path, routeType, ...rest } = props;

    const user = useAuth();

    const renderComponent = (routeProps: RouteProps) => {
        switch (routeType) {
            case "PRIVATE":
                if (user.isAuthenticated) {
                    return <Component {...routeProps}></Component>
                } else {
                    return <Navigate to="/login"></Navigate>
                }
            case "GUEST":
                if (!user.isAuthenticated) {
                    return <Component {...routeProps}></Component>
                } else {
                    return <Navigate to="/user"></Navigate>
                }
            case "PUBLIC":
                return <Component {...routeProps}></Component>
        }
    }

    return (
        <Route {...rest} path={path || ""} render={(routeProps: any) => renderComponent(routeProps)}></Route>
    )
}

export default AppRoute