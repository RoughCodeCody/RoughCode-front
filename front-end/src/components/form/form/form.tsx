import { zodResolver } from "@hookform/resolvers/zod";
import { ReactNode } from "react";
import {
  useForm,
  FieldValues,
  SubmitHandler,
  UseFormReturn,
  UseFormProps,
} from "react-hook-form";
import { ZodTypeAny } from "zod";

import { StyledForm } from "./style";

type FormProps<
  TFormValues extends FieldValues = FieldValues,
  Schema extends ZodTypeAny = ZodTypeAny
> = {
  onSubmit: SubmitHandler<TFormValues>;
  children: (methods: UseFormReturn<TFormValues>) => ReactNode;
  options?: UseFormProps<TFormValues>;
  id?: string;
  schema?: Schema;
};

export const Form = <
  TFormValues extends FieldValues = FieldValues,
  Schema extends ZodTypeAny = ZodTypeAny
>({
  onSubmit,
  children,
  options,
  id,
  schema,
}: FormProps<TFormValues, Schema>) => {
  const methods = useForm<TFormValues>({
    ...options,
    resolver: schema && zodResolver(schema),
  });
  return (
    <StyledForm onSubmit={methods.handleSubmit(onSubmit)} id={id}>
      {children(methods)}
    </StyledForm>
  );
};
