import { useEffect, useState } from "react";
import { SubmitHandler, UseFormReturn } from "react-hook-form";

import { useProjectFeedbackSelectionStore } from "@/features/feedbacks";
import { FlexDiv } from "@/components/elements";
import { InputField } from "@/components/form";
import { TiptapController } from "@/features/rich-text-editor";
import { TagSearch } from "@/features/search";
import { useSearchCriteriaStore } from "@/stores";

import { useCheckProjectUrl } from "../../api";
import { ProjectUpdateValues } from "../../types";
import { UrlInspectionBtn, SubmitButtonWrapper, SubmitButton } from "./style";
import { ThumbnailField } from "./thumbnail-field";
import { InputSuccessMsg, InputErrorMsg } from "@/components/form";

type ProjectUpdateFormFieldsProps = {
  methods: UseFormReturn<ProjectUpdateValues>;
  projectId: number;
  projectUpdateInitialValues?: ProjectUpdateValues & { img: string };
  onSubmit: SubmitHandler<ProjectUpdateValues>;
};

export const ProjectUpdateFormFields = ({
  methods,
  projectId,
  projectUpdateInitialValues,
  onSubmit,
}: ProjectUpdateFormFieldsProps) => {
  const {
    register,
    formState: { isValid, dirtyFields, errors },
    control,
    setValue,
    getValues,
    handleSubmit,
    watch,
    getFieldState,
  } = methods;

  const urlFieldState = getFieldState("url");

  const [isValidUrl, setIsValidUrl] = useState(false);
  const [isFixedUrl, setIsFixedUrl] = useState("");

  const { searchCriteria } = useSearchCriteriaStore();
  const { selectedProjectFeedbackId } = useProjectFeedbackSelectionStore();
  const checkProjectUrlQuery = useCheckProjectUrl({
    url: watch("url"),
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

  const onUrlInspectionBtnClick = async (e) => {
    e.preventDefault();
    e.target.disabled = true;
    const query = await checkProjectUrlQuery.refetch();
    if (query.data) {
      console.log(query.data);
      setIsValidUrl(true);
      // setIsFixedUrl(getValues("url"));
    } else {
      setIsValidUrl(false);
      e.target.disabled = false;
    }
    // console.log(formState.errors);
  };

  const isThumbnail = () => {
    const inputThumbnail = document.getElementById(
      "input-thumbnail"
    ) as HTMLInputElement;

    return Boolean(inputThumbnail?.files?.item(0));
  };

  return (
    <>
      <InputField
        type="text"
        label="제목"
        isDirty={dirtyFields["title"]}
        error={errors["title"]}
        registration={register("title")}
        inputContainerWidth="80%"
      />
      <InputField
        type="text"
        label="공지"
        isDirty={dirtyFields["notice"]}
        error={errors["notice"]}
        registration={register("notice")}
        inputContainerWidth="80%"
      />
      <InputField
        type="text"
        label="한 줄 소개"
        isDirty={dirtyFields["introduction"]}
        error={errors["introduction"]}
        registration={register("introduction")}
        inputContainerWidth="80%"
      />
      <FlexDiv
        direction="column"
        justify="flex-start"
        align="flex-start"
        width="100%"
        gap="0.5rem"
      >
        <FlexDiv direction="row" justify="flex-start" width="100%" gap="2rem">
          <InputField
            type="text"
            label="URL"
            isDirty={dirtyFields["url"]}
            error={errors["url"]}
            registration={register("url")}
            inputContainerWidth="80%"
          />
          <UrlInspectionBtn
            onClick={onUrlInspectionBtnClick}
            disabled={watch("url") === "" || Boolean(errors.url)}
          >
            검사
          </UrlInspectionBtn>
        </FlexDiv>
        {isValidUrl ? (
          <InputSuccessMsg>
            검사 성공! 다음은 유효한 URL이에요.
            <br />
            {watch("url")}
          </InputSuccessMsg>
        ) : (
          <></>
        )}
      </FlexDiv>

      <TagSearch whichTag="project" />
      {projectUpdateInitialValues ? (
        <TiptapController<ProjectUpdateValues>
          name="content"
          control={control}
          initialValue={projectUpdateInitialValues.content}
        />
      ) : (
        <TiptapController<ProjectUpdateValues>
          name="content"
          control={control}
        />
      )}

      {projectUpdateInitialValues ? (
        <ThumbnailField key="new" initialSrc={projectUpdateInitialValues.img} />
      ) : (
        <ThumbnailField key="old" />
      )}

      <SubmitButtonWrapper>
        <SubmitButton
          id="submit-btn"
          type="submit"
          onClick={handleSubmit(onSubmit)}
          disabled={!isValid || !isValidUrl || !isThumbnail()}
        >
          확인
        </SubmitButton>
      </SubmitButtonWrapper>
    </>
  );
};
