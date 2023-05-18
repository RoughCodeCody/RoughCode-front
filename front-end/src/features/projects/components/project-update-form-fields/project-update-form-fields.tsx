import { useRouter } from "next/router";
import { SubmitHandler, UseFormReturn } from "react-hook-form";
import { useEffect, useState } from "react";
import { FlexDiv } from "@/components/elements";
import { InputField, InputSuccessMsg, InputErrorMsg } from "@/components/form";
import { useProjectFeedbackSelectionStore } from "@/features/feedbacks";
import { TiptapController } from "@/features/rich-text-editor";
import { TagSearch } from "@/features/search";
import { useSearchCriteriaStore } from "@/stores";
import { useCheckProjectUrl } from "../../api";
import { ProjectUpdateValues } from "../../types";
import { UrlInspectionBtn, SubmitButtonWrapper, SubmitButton } from "./style";
import { ThumbnailField } from "./thumbnail-field";

type ProjectUpdateFormFieldsProps = {
  methods: UseFormReturn<ProjectUpdateValues>;
  projectId: number;
  projectUpdateInitialValues?: ProjectUpdateValues & {
    img: string;
    tags: {
      tagId: number;
      name: string;
      cnt: number;
    }[];
  };
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
    formState: { isValid, dirtyFields, errors, isSubmitting },
    control,
    setValue,
    handleSubmit,

    watch,
  } = methods;
  const router = useRouter();
  const [isValidUrl, setIsValidUrl] = useState(false);
  const [fixedUrl, setFixedUrl] = useState("");
  const [isThumb, setIsThumb] = useState(false);
  const { searchCriteria, addTagId, reset } = useSearchCriteriaStore();
  const { selectedProjectFeedbackId } = useProjectFeedbackSelectionStore();
  const checkProjectUrlQuery = useCheckProjectUrl({
    url: watch("url"),
    config: { enabled: false },
  });
  const dynamicRoute = router.asPath;

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
        shouldValidate: true,
      });
      setValue("notice", projectUpdateInitialValues.notice, {
        shouldDirty: true,
        shouldValidate: true,
      });
      setValue("introduction", projectUpdateInitialValues.introduction, {
        shouldDirty: true,
        shouldValidate: true,
      });
      setValue("url", projectUpdateInitialValues.url, {
        shouldDirty: true,
        shouldValidate: true,
      });
      setValue("projectId", projectUpdateInitialValues.projectId, {
        shouldDirty: true,
        shouldValidate: true,
      });
      setValue("selectedFeedbacksId", [], {
        shouldDirty: true,
        shouldValidate: true,
      });
      setValue("selectedTagsId", projectUpdateInitialValues.selectedTagsId);
      setValue("content", projectUpdateInitialValues.content, {
        shouldDirty: true,
        shouldValidate: true,
      });
    }
  }, [projectUpdateInitialValues, setValue, addTagId, searchCriteria]);

  useEffect(() => reset(), [dynamicRoute, reset]);

  register("projectId");
  register("selectedTagsId");
  register("selectedFeedbacksId");

  useEffect(() => {
    if (projectUpdateInitialValues) {
      projectUpdateInitialValues.tags?.forEach((tag) => {
        const arg = { tagId: tag.tagId, name: tag.name };
        addTagId(arg);
      });
    }
  }, [addTagId, projectUpdateInitialValues]);

  const onUrlInspectionBtnClick = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    const eventTarget = e.target as HTMLButtonElement;
    eventTarget.disabled = true;
    const query = await checkProjectUrlQuery.refetch();
    if (query.data) {
      setIsValidUrl(true);
      setFixedUrl(watch("url"));
    } else {
      setIsValidUrl(false);
      eventTarget.disabled = false;
    }
    // console.log(formState.errors);
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
            {fixedUrl}
          </InputSuccessMsg>
        ) : (
          <InputSuccessMsg>검사를 완료해야 업로드할 수 있어요.</InputSuccessMsg>
        )}
      </FlexDiv>

      <TagSearch whichTag="project" />
      {/* {projectUpdateInitialValues ? ( */}
      <TiptapController<ProjectUpdateValues>
        name="content"
        control={control}
        initialValue={projectUpdateInitialValues?.content || ""}
      />

      {projectUpdateInitialValues ? (
        <ThumbnailField
          key="new"
          initialSrc={projectUpdateInitialValues.img}
          setIsThumb={setIsThumb}
        />
      ) : (
        <ThumbnailField key="old" setIsThumb={setIsThumb} />
      )}

      <SubmitButtonWrapper>
        <SubmitButton
          id="submit-btn"
          type="submit"
          onClick={handleSubmit(onSubmit)}
          disabled={!isValid || !isValidUrl || !isThumb || isSubmitting}
        >
          확인
        </SubmitButton>
      </SubmitButtonWrapper>
    </>
  );
};
