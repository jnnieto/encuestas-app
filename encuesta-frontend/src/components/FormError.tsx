const FormError = ({ children }: any) => {
  return (
    <p className="mt-2 text-sm text-red-600">
      { children }
    </p>
  );
};

export default FormError;
