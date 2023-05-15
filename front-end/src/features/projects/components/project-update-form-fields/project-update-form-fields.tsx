import { useEffect } from "react";
import { UseFormReturn } from "react-hook-form";

import { useProjectFeedbackSelectionStore } from "@/features/feedbacks";
import { InputField } from "@/components/form";
import { TiptapController } from "@/features/rich-text-editor";
import { TagSearch } from "@/features/search";
import { useSearchCriteriaStore } from "@/stores";

import { ProjectUpdateValues } from "../../types";
import { SubmitButtonWrapper, SubmitButton } from "./style";

type ProjectUpdateFormFieldsProps = {
  methods: UseFormReturn<ProjectUpdateValues>;
  projectId: number;
};

export const ProjectUpdateFormFields = ({
  methods,
  projectId,
}: ProjectUpdateFormFieldsProps) => {
  const { register, formState, control, setValue } = methods;

  const { searchCriteria } = useSearchCriteriaStore();
  const { selectedProjectFeedbackId } = useProjectFeedbackSelectionStore();

  useEffect(() => {
    setValue("projectId", projectId);
    setValue(
      "selectedTagsId",
      searchCriteria.tagIdList.map((el) => el.tagId)
    );
    setValue("selectedFeedbacksId", selectedProjectFeedbackId);
  }, [
    setValue,
    projectId,
    searchCriteria.tagIdList,
    selectedProjectFeedbackId,
  ]);

  register("projectId");
  register("selectedTagsId");
  register("selectedFeedbacksId");

  return (
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

      <TiptapController<ProjectUpdateValues> name="content" control={control} />
      <TagSearch whichTag="project" />
      <input id="input-thumbnail" type="file" />

      <SubmitButtonWrapper>
        <SubmitButton type="submit" value="확인" />
      </SubmitButtonWrapper>
    </>
  );
};
