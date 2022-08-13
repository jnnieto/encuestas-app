import { Field, Form, Formik } from "formik";
import { useState } from "react";
import * as Yup from "yup";
import FormError from "../components/FormError";
import Loading from "../components/requests/Loading";
import Alert from "../components/responses/Alert";
import { useAuthDispatch } from "../hooks/useAuth";
import { Login } from "../interfaces/login";
import { login } from "../services/UserService";

const Register = () => {

  const [error, setError] = useState<string>("");
  const [showAlert, setShowAlert] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);

  const authDispatch = useAuthDispatch();

  const newUserSchema = Yup.object().shape({
    email: Yup.string()
      .required("El nombre de usuario es obligatorio")
      .email("El correo debe ser válido"),
    password: Yup.string()
      .required("La contraseña es obligatoria")
      .min(8, "La contraseña debe tener mínimo 8 caracteres")
      .max(40, "La contraseña debe tener máximo 40 caracteres"),
  });

  const handleLogin = async (values: Login) => {
    setLoading(true);
    try {
      const res = await login(values);
      const token = res.data.token;
      authDispatch({
        type: 'login',
        token
      })
    } catch (error: any) {
      if (error.response) {
        error.response.status === 403 && setError('Correo o contraseña incorrectos')
      }
      setShowAlert(true);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="h-screen bg-gray-900">
      {showAlert && <Alert error={error} setShowAlert={setShowAlert} />}
      <div className="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
        <div className="bg-gray-50 rounded-xl max-w-md w-full space-y-8">
          <h1 className="mt-6 text-center font-bold text-lg uppercase text-gray-500">
            Iniciar sesión
          </h1>
          <Formik
            initialValues={{
              email: "",
              password: "",
            }}
            onSubmit={async (values) => {
              await handleLogin(values);
            }}
            validationSchema={newUserSchema}
          >
            {({ errors, touched }) => (
              <Form>
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
                    "Iniciar sesión"
                  ) : <Loading>{'Iniciando sesión'}</Loading>}
                </button>
              </Form>
            )}
          </Formik>
        </div>
      </div>
    </div>
  );
};

export default Register;
