import { FieldError, UseFormRegisterReturn } from "react-hook-form";

import { Input, InputContainer, InputLabel, InputErrorMsg } from "./style";

type InputFieldProps = {
  type: "text" | "email";
  label: string;
  isDirty: boolean | undefined;
  error: FieldError | undefined;
  registration: UseFormRegisterReturn;
  inputContainerWidth?: string;
};

export const InputField = (props: InputFieldProps) => {
  const { type, label, isDirty, error, registration, inputContainerWidth } =
    props;

  return (
    <InputContainer width={inputContainerWidth}>
      <Input type={type} {...registration} />
      <InputLabel htmlFor={registration.name} isDirty={isDirty}>
        {label}
      </InputLabel>
      {error?.message && (
        <InputErrorMsg role="alert" aria-label={error?.message}>
          {error?.message}
        </InputErrorMsg>
      )}
    </InputContainer>
  );
};
