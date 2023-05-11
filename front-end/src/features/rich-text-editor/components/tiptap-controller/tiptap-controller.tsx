import {
  useController,
  FieldValues,
  UseControllerProps,
} from "react-hook-form";

import { TiptapEditor } from "../tiptap-editor";

export const TiptapController = <
  TFormValues extends FieldValues = FieldValues
>({
  name,
  control,
}: UseControllerProps<TFormValues>) => {
  const {
    field: { onChange },
  } = useController({ name, control });
  return <TiptapEditor onChange={onChange} />;
};
