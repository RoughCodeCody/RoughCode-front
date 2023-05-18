import { SubmitHandler } from "react-hook-form";
import * as z from "zod";

import { Form } from "@/components/form";

import { CodeUpdateValues } from "../../types";
import { CodeUpdateFormFields } from "./code-update-form-fields";

const schema = z.object({
  title: z.string().min(1, "필요"),
  githubUrl: z.string().url("필요"),
  content: z.string(),
  codeId: z.number(),
  projectId: z.number().nullable(),
  selectedTagsId: z.number().array().nullable(),
  selectedReviewsId: z.number().array().nullable(),
  language: z.string().min(1, "필요"),
});

type CodeUpdateFormProps = {
  codeId: number;
  onSubmit: SubmitHandler<CodeUpdateValues>;
  codeUpdateInitialValues?: CodeUpdateValues;
};

export const CodeUpdateForm = ({
  codeId,
  onSubmit,
  codeUpdateInitialValues,
}: CodeUpdateFormProps) => {
  return (
    <div>
      <Form<CodeUpdateValues, typeof schema>
        schema={schema}
        onSubmit={onSubmit}
        options={{
          shouldUnregister: true,
          defaultValues: {
            title: "",
            githubUrl: "",
            content: "",
            projectId: null,
            selectedTagsId: null,
            selectedReviewsId: null,
            language: "",
          },
        }}
      >
        {(methods) => (
          <CodeUpdateFormFields
            methods={methods}
            codeId={codeId}
            codeUpdateInitialValues={codeUpdateInitialValues}
            onSubmit={onSubmit}
          />
        )}
      </Form>
    </div>
  );
};
