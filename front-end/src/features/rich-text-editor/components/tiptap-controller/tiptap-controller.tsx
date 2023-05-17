import { useController, Control, FieldValues, Path } from "react-hook-form";

import { TiptapEditor } from "../tiptap-editor";

type TiptapControllerProps<TFormValues extends FieldValues = FieldValues> = {
  name: Path<TFormValues>;
  control?: Control<TFormValues>;
  initialValue?: string;
};

export const TiptapController = <
  TFormValues extends FieldValues = FieldValues
>({
  name,
  control,
  initialValue,
}: TiptapControllerProps<TFormValues>) => {
  const {
    field: { onChange },
  } = useController({ name, control });
  return <TiptapEditor onChange={onChange} initialValue={initialValue} />;
};
