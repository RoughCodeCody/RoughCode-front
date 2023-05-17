import { useEffect, useState } from "react";
import { UseFormReturn } from "react-hook-form";

import { useProjectFeedbackSelectionStore } from "@/features/feedbacks";
import { FlexDiv } from "@/components/elements";
import { InputField } from "@/components/form";
import { TiptapController } from "@/features/rich-text-editor";
import { TagSearch } from "@/features/search";
import { useSearchCriteriaStore } from "@/stores";

import { useCheckProjectUrl } from "../../api";
import { ProjectUpdateValues } from "../../types";
import { UrlInspectionBtn, SubmitButtonWrapper, SubmitButton } from "./style";

type ProjectUpdateFormFieldsProps = {
  methods: UseFormReturn<ProjectUpdateValues>;
  projectId: number;
  projectUpdateInitialValues?: ProjectUpdateValues;
};

export const ProjectUpdateFormFields = ({
  methods,
  projectId,
  projectUpdateInitialValues,
}: ProjectUpdateFormFieldsProps) => {
  const { register, formState, control, setValue, getValues } = methods;

  const { searchCriteria } = useSearchCriteriaStore();
  const { selectedProjectFeedbackId } = useProjectFeedbackSelectionStore();
  const checkProjectUrlQuery = useCheckProjectUrl({
    url: getValues("url"),
    config: { enabled: false },
  });
  const [submitBtnDisabled, setSubmitBtnDisabled] = useState(true);
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
  useEffect(() => {
    if (projectUpdateInitialValues) {
      setValue("title", projectUpdateInitialValues.title, {
        shouldDirty: true,
      });
      setValue("notice", projectUpdateInitialValues.notice, {
        shouldDirty: true,
      });
      setValue("introduction", projectUpdateInitialValues.introduction, {
        shouldDirty: true,
      });
      setValue("url", projectUpdateInitialValues.url, { shouldDirty: true });
      setValue("projectId", projectUpdateInitialValues.projectId, {
        shouldDirty: true,
      });
      setValue(
        "selectedFeedbacksId",
        projectUpdateInitialValues.selectedFeedbacksId,
        { shouldDirty: true }
      );
      setValue("selectedTagsId", projectUpdateInitialValues.selectedTagsId, {
        shouldDirty: true,
      });
    }
  }, [projectUpdateInitialValues, setValue]);

  register("projectId");
  register("selectedTagsId");
  register("selectedFeedbacksId");

  const onUrlInspectionBtnClick = () => {
    checkProjectUrlQuery.refetch();

    if (checkProjectUrlQuery.data) {
      setSubmitBtnDisabled(false);
    }
  };

  return (
    <>
      <InputField
        type="text"
        label="제목"
        isDirty={formState.dirtyFields["title"]}
        error={formState.errors["title"]}
        registration={register("title")}
        inputContainerWidth="80%"
      />
      <InputField
        type="text"
        label="공지"
        isDirty={formState.dirtyFields["notice"]}
        error={formState.errors["notice"]}
        registration={register("notice")}
        inputContainerWidth="80%"
      />
      <InputField
        type="text"
        label="한 줄 소개"
        isDirty={formState.dirtyFields["introduction"]}
        error={formState.errors["introduction"]}
        registration={register("introduction")}
        inputContainerWidth="80%"
      />
      <FlexDiv direction="row" justify="flex-start" width="100%" gap="2rem">
        <InputField
          type="text"
          label="URL"
          isDirty={formState.dirtyFields["url"]}
          error={formState.errors["url"]}
          registration={register("url")}
          inputContainerWidth="80%"
        />
        <UrlInspectionBtn onClick={onUrlInspectionBtnClick}>
          검사
        </UrlInspectionBtn>
      </FlexDiv>
      {projectUpdateInitialValues?.content ? (
        <TiptapController<ProjectUpdateValues>
          name="content"
          control={control}
          initialValue={projectUpdateInitialValues.content}
        />
      ) : null}
      <TagSearch whichTag="project" />
      <input id="input-thumbnail" type="file" />

      <SubmitButtonWrapper>
        <SubmitButton
          id="submit-btn"
          type="submit"
          value="확인"
          disabled={submitBtnDisabled}
        />
      </SubmitButtonWrapper>
    </>
  );
};
