import * as React from "react";
import * as z from "zod";
import { SubmitHandler } from "react-hook-form";

import { Form } from "@/components/form";

import { ProjectUpdateValues } from "../../types";
import { ProjectUpdateFormFields } from "../project-update-form-fields";

const schema = z.object({
  title: z.string().min(1, "필요"),
  introduction: z.string().min(1, "필요"),
  content: z.string(),
  url: z.string().url("필요"),
  notice: z.string().min(1, "필요"),
  projectId: z.number(),
  selectedTagsId: z.number().array().nullable(),
  selectedFeedbacksId: z.number().array().nullable(),
});

type ProjectUpdateFormProps = {
  projectId: number;
  onSubmit: SubmitHandler<ProjectUpdateValues>;
  projectUpdateInitialValues?: ProjectUpdateValues & {
    img: string;
    tags: {
      tagId: number;
      name: string;
      cnt: number;
    }[];
  };
};

export const ProjectUpdateForm = ({
  projectId,
  onSubmit,
  projectUpdateInitialValues,
}: ProjectUpdateFormProps) => {
  return (
    <div>
      <Form<ProjectUpdateValues, typeof schema>
        onSubmit={onSubmit}
        schema={schema}
        options={{
          shouldUnregister: true,
          defaultValues: {
            title: "",
            introduction: "",
            url: "",
            content: "",
            notice: "",
            selectedTagsId: null,
            selectedFeedbacksId: null,
          },
          mode: "onChange",
        }}
      >
        {(methods) => (
          <ProjectUpdateFormFields
            methods={methods}
            projectId={projectId}
            projectUpdateInitialValues={projectUpdateInitialValues}
            onSubmit={onSubmit}
          />
        )}
      </Form>
    </div>
  );
};
