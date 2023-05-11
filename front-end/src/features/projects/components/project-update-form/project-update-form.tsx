import * as React from "react";
import * as z from "zod";

import { Btn } from "@/components/elements";
import { Form, InputField } from "@/components/form";
import { TiptapEditor } from "@/features/rich-text-editor";
import { TagSearch } from "@/features/search";
import { useSearchCriteriaStore } from "@/stores";

const schema = z.object({
  title: z.string().min(1, "Required"),
  introduction: z.string().min(1, "Required"),
  content: z.string().min(1, "Required"),
  url: z.string().min(1, "Required"),
  notice: z.string().min(1, "Required"),
  projectId: z.number().min(1, "Required"),
  selectedTagsId: z.number().array(),
  selectedFeedbacksId: z.number().array(),
});

type ProjectUpdateValues = {
  title: string;
  notice: string;
  introduction: string;
  content: string; // hou
  url: string;
  projectId: number | ""; // hou
  selectedTagsId?: number[] | "";
  selectedFeedbacksId?: number[] | "";
};

type ProjectUpdateFormProps = {
  onSuccess: () => void;
};

export const ProjectUpdateForm = ({ onSuccess }: ProjectUpdateFormProps) => {
  const { searchCriteria } = useSearchCriteriaStore();
  console.log(searchCriteria.tagIdList);

  return (
    <div>
      <Form<ProjectUpdateValues, typeof schema>
        onSubmit={async (values) => {
          //   await register(values);
          onSuccess();
        }}
        schema={schema}
        options={{
          shouldUnregister: true,
          defaultValues: {
            title: "",
            introduction: "",
            content: "",
            url: "",
            notice: "",
            projectId: "",
            selectedTagsId: "",
            selectedFeedbacksId: "",
          },
        }}
      >
        {({ register, formState }) => (
          <>
            <InputField
              type="text"
              label="제목"
              isDirty={formState.dirtyFields["title"]}
              error={formState.errors["title"]}
              registration={register("title")}
            />
            <InputField
              type="text"
              label="공지"
              isDirty={formState.dirtyFields["notice"]}
              error={formState.errors["notice"]}
              registration={register("notice")}
            />
            <InputField
              type="text"
              label="한 줄 소개"
              isDirty={formState.dirtyFields["introduction"]}
              error={formState.errors["introduction"]}
              registration={register("introduction")}
            />
            <InputField
              type="text"
              label="URL"
              isDirty={formState.dirtyFields["url"]}
              error={formState.errors["url"]}
              registration={register("url")}
            />

            <TiptapEditor />
            <TagSearch />

            <div>
              <Btn text="Register" />
              {/* isLoading={isRegistering} type="submit" className="w-full" */}
            </div>
          </>
        )}
      </Form>
    </div>
  );
};
