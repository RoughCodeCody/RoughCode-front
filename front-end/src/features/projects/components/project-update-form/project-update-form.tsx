import * as React from "react";
import * as z from "zod";

import { Form } from "@/components/form";

import { ProjectUpdateValues } from "../../types";
import { ProjectUpdateFormFields } from "../project-update-form-fields";

const schema = z.object({
  title: z.string().min(1, "필요"),
  introduction: z.string().min(1, "필요"),
  content: z.string().min(1, "필요"),
  url: z.string().url("필요"),
  notice: z.string().min(1, "필요"),
  projectId: z.number(),
  selectedTagsId: z.number().array(),
  selectedFeedbacksId: z.number().array(),
});

type ProjectUpdateFormProps = {
  projectId: number;
  onSubmit: (values: ProjectUpdateValues) => void;
};

export const ProjectUpdateForm = ({
  projectId,
  onSubmit,
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
            notice: "",
          },
        }}
      >
        {(methods) => (
          <ProjectUpdateFormFields methods={methods} projectId={projectId} />
        )}
      </Form>
    </div>
  );
};