import { UseFormReturn, SubmitHandler } from "react-hook-form";
import { useEffect, useState } from "react";

import { CodeEditor } from "@/features/code-editor";
import { FlexDiv } from "@/components/elements";
import { InputField } from "@/components/form";
import { TiptapController } from "@/features/rich-text-editor";
import { TagSearch } from "@/features/search";
import { useSearchCriteriaStore } from "@/stores";
import { convertGitHubUrl } from "@/util/convert-github-url";

import { useCode } from "../../api/get-code";
import { useCodeReviewsForCodeUpdateSelection } from "../../stores/code-review-for-code-update-selection";
import { CodeUpdateValues } from "../../types";
import {
  GitHubBtn,
  SubmitButtonWrapper,
  SubmitButton,
} from "./code-update-form-fields-style";
import { isValid } from "zod";

type CodeUpdateFormFieldsProps = {
  methods: UseFormReturn<CodeUpdateValues>;
  codeId: number;
  codeUpdateInitialValues?: CodeUpdateValues;
  onSubmit: SubmitHandler<CodeUpdateValues>;
};

export const CodeUpdateFormFields = ({
  methods,
  codeId,
  codeUpdateInitialValues,
  onSubmit,
}: CodeUpdateFormFieldsProps) => {
  const { register, formState, control, setValue, watch, handleSubmit } =
    methods;

  const [isValidUrl, setIsValidUrl] = useState(false);
  const { searchCriteria } = useSearchCriteriaStore();
  const { selectedCodeReviewId } = useCodeReviewsForCodeUpdateSelection();
  const codeQuery = useCode({
    githubUrl: convertGitHubUrl(watch("githubUrl")),
    config: { enabled: false },
  });

  // const [submitBtnDisabled, setSubmitBtnDisabled] = useState(true);
  useEffect(() => {
    setValue("codeId", codeId);
    setValue("projectId", null);
    setValue(
      "selectedTagsId",
      searchCriteria.tagIdList.map((el) => el.tagId)
    );
    setValue("selectedReviewsId", selectedCodeReviewId);
  }, [setValue, codeId, searchCriteria.tagIdList, selectedCodeReviewId]);
  useEffect(() => {
    if (codeUpdateInitialValues) {
      setValue("title", codeUpdateInitialValues.title, {
        shouldDirty: true,
      });
      setValue("githubUrl", codeUpdateInitialValues.githubUrl, {
        shouldDirty: true,
      });
      setValue("content", codeUpdateInitialValues.content, {
        shouldDirty: true,
      });
      // setValue("projectId", codeUpdateInitialValues.projectId, {
      //   shouldDirty: true,
      // });

      setValue("selectedTagsId", codeUpdateInitialValues.selectedTagsId, {
        shouldDirty: true,
      });
      setValue("selectedReviewsId", codeUpdateInitialValues.selectedReviewsId, {
        shouldDirty: true,
      });
      setValue("language", codeUpdateInitialValues.language, {
        shouldDirty: true,
      });
    }
  }, [codeUpdateInitialValues, setValue]);

  register("codeId");
  register("projectId");
  register("selectedTagsId");
  register("selectedReviewsId");

  const onGitHubBtnClick = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    const eventTarget = e.target as HTMLButtonElement;
    const query = await codeQuery.refetch();
    eventTarget.disabled = true;

    if (query?.status === "success") {
      setIsValidUrl(true);
    }
    if (query?.status === "error") {
      eventTarget.disabled = false;
      setIsValidUrl(false);
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

      <FlexDiv direction="row" justify="flex-start" width="100%" gap="2rem">
        <InputField
          type="text"
          label="GitHub URL"
          isDirty={formState.dirtyFields["githubUrl"]}
          error={formState.errors["githubUrl"]}
          registration={register("githubUrl")}
          inputContainerWidth="80%"
        />

        <GitHubBtn onClick={onGitHubBtnClick}>불러오기</GitHubBtn>
      </FlexDiv>
      <InputField
        type="text"
        label="Language"
        isDirty={formState.dirtyFields["language"]}
        error={formState.errors["language"]}
        registration={register("language")}
        inputContainerWidth="80%"
      />
      <TagSearch whichTag="code" />
      {codeQuery.data ? (
        <CodeEditor
          key="new"
          headerText="GitHub에서 불러온 코드입니다."
          lineSelection={false}
          height="30rem"
          language={"javascript"}
          originalCode={codeQuery.data.content || ""}
          noShad={true}
        />
      ) : (
        <CodeEditor
          key="old"
          headerText="GitHub URL을 입력하고 불러오기를 누르세요"
          lineSelection={false}
          height="30rem"
          language={"javascript"}
          originalCode={""}
          noShad={true}
        />
      )}

      {codeUpdateInitialValues ? (
        <TiptapController<CodeUpdateValues>
          name="content"
          control={control}
          initialValue={codeUpdateInitialValues.content}
        />
      ) : (
        <TiptapController<CodeUpdateValues> name="content" control={control} />
      )}

      <SubmitButtonWrapper>
        <SubmitButton
          id="submit-btn"
          onClick={handleSubmit(onSubmit)}
          disabled={!formState.isValid || !isValidUrl}
        >
          확인
        </SubmitButton>
      </SubmitButtonWrapper>
    </>
  );
};
