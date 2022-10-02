import { ErrorMessage, Field, Form, Formik } from "formik";
import { useState } from "react";
import * as Yup from "yup";
import FormError from "../components/FormError";
import Loading from "../components/requests/Loading";
import Alert from "../components/responses/Alert";
import { useAuthDispatch } from "../hooks/useAuth";
import { UserResgister } from "../interfaces/user-register";
import { login, register } from "../services/UserService";

const Login = () => {

  const [error, setError] = useState<string>("");
  const [showAlert, setShowAlert] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);

  const authDispatch = useAuthDispatch();

  const newUserSchema = Yup.object().shape({
    name: Yup.string()
      .required("El nombre es obligatorio")
      .min(3, "El nombre debe tener mínimo 3 caracteres")
      .max(15, "El nombre debe tener máximo 15 caracteres"),
    lastName: Yup.string()
      .required("El apellido es obligatorio")
      .min(3, "El apellido debe tener mínimo 3 caracteres")
      .max(15, "El apellido debe tener máximo 15 caracteres"),
    userName: Yup.string()
      .required("El nombre de usuario es obligatorio")
      .min(5, "El nombre de usuario debe tener mínimo 5 caracteres")
      .max(10, "El nombre de usuario debe tener máximo 10 caracteres"),
    email: Yup.string()
      .required("El nombre de usuario es obligatorio")
      .email("El correo debe ser válido"),
    password: Yup.string()
      .required("La contraseña es obligatoria")
      .min(8, "La contraseña debe tener mínimo 8 caracteres")
      .max(40, "La contraseña debe tener máximo 40 caracteres"),
  });

  const handleRegister = async (values: UserResgister) => {
    setLoading(true);
    try {
      await register(values);
      const {email, password} = values;
      const res = await login({email, password});
      const token = res.data.token;
      authDispatch({
        type: 'login',
        token
      })
    } catch (error: any) {
      if (error.response) {
        error.response.status === 400 && setError(error.response.data.errors.email);
      }
      setShowAlert(true);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="h-full bg-gray-900">
      {showAlert && <Alert error={error} setShowAlert={setShowAlert} />}
      <div className="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
        <div className="bg-gray-50 rounded-xl max-w-md w-full space-y-8">
          <h1 className="mt-6 text-center font-bold text-lg uppercase text-gray-500">
            Crear cuenta
          </h1>
          <Formik
            initialValues={{
              name: "",
              lastName: "",
              userName: "",
              email: "",
              password: "",
            }}
            onSubmit={async (values) => {
              await handleRegister(values);
            }}
            validationSchema={newUserSchema}
          >
            {({ errors, touched }) => (
              <Form>
                <div className="px-3 mb-5">
                  <label
                    className="block mb-2 text-sm font-medium text-gray-700"
                    htmlFor="name"
                  >
                    Nombre
                  </label>
                  <div className="relative">
                    <div className="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                      <svg
                        className="w-5 h-5 text-gray-500"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                        ></path>
                      </svg>
                    </div>
                    <Field
                      type="text"
                      name="name"
                      id="name"
                      className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5"
                      placeholder="Ingrese el nombre"
                    />
                  </div>
                  {errors.name && touched.name ? (
                    <FormError>{errors.name}</FormError>
                  ) : null}
                </div>
                <div className="px-3 mb-5">
                  <label
                    className="block mb-2 text-sm font-medium text-gray-700"
                    htmlFor="lastName"
                  >
                    Apellido
                  </label>
                  <div className="relative">
                    <div className="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                      <svg
                        className="w-5 h-5 text-gray-500"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                        ></path>
                      </svg>
                    </div>
                    <Field
                      type="text"
                      name="lastName"
                      id="lastName"
                      className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5"
                      placeholder="Ingrese el apellido"
                    />
                  </div>
                  {errors.lastName && touched.lastName ? (
                    <FormError>{errors.lastName}</FormError>
                  ) : null}
                </div>
                <div className="px-3 mb-5">
                  <label
                    className="block mb-2 text-sm font-medium text-gray-700"
                    htmlFor="userName"
                  >
                    Nombre de usuario
                  </label>
                  <div className="relative">
                    <div className="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                      <svg
                        className="w-5 h-5 text-gray-500"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                        ></path>
                      </svg>
                    </div>
                    <Field
                      type="text"
                      name="userName"
                      id="userName"
                      className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5"
                      placeholder="Ingrese el nombre de usuario"
                    />
                  </div>
                  {errors.userName && touched.userName ? (
                    <FormError>{errors.userName}</FormError>
                  ) : null}
                </div>
                <div className="px-3 mb-5">
                  <label
                    className="block mb-2 text-sm font-medium text-gray-700"
                    htmlFor="email"
                  >
                    Correo electrónico
                  </label>
                  <div className="relative">
                    <div className="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                      <svg
                        aria-hidden="true"
                        className="w-5 h-5 text-gray-500"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"></path>
                        <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"></path>
                      </svg>
                    </div>
                    <Field
                      type="email"
                      name="email"
                      id="email"
                      className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5"
                      placeholder="Ingrese el correo"
                    />
                  </div>
                  {errors.email && touched.email ? (
                    <FormError>{errors.email}</FormError>
                  ) : null}
                </div>
                <div className="px-3 mb-5">
                  <label
                    className="block mb-2 text-sm font-medium text-gray-700"
                    htmlFor="password"
                  >
                    Contraseña
                  </label>
                  <div className="relative">
                    <div className="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                      <svg
                        className="w-5 h-5 text-gray-500"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                        ></path>
                      </svg>
                    </div>
                    <Field
                      type="password"
                      name="password"
                      id="password"
                      className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5"
                      placeholder="Ingrese la contraseña"
                    />
                  </div>
                  {errors.password && touched.password ? (
                    <FormError>{errors.password}</FormError>
                  ) : null}
                </div>
                <button
                  type="submit"
                  className="mx-auto block bg-sky-600 hover:bg-sky-500 transition py-2 px-10 text-center mb-5 text-white font-bold uppercase rounded-md"
                >
                  { !loading ? (
                    "Registrar"
                  ) : <Loading>{'Creando cuenta'}</Loading>}
                </button>
              </Form>
            )}
          </Formik>
        </div>
      </div>
    </div>
  );
};

export default Login;
