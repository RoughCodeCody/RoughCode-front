import { useRouter } from "next/router";
import { UseFormReturn, SubmitHandler } from "react-hook-form";
import { useEffect, useState } from "react";
import { queryClient } from "@/lib/react-query";

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
  SubHeading,
} from "./code-update-form-fields-style";
import { getLanguageTags } from "@/features/search";
import { LanguageSearch } from "@/features/search/components/language-search";
import {
  InputSuccessMsg,
  InputErrorMsg,
} from "@/components/form/input-field/style";

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
  const [isClicked, setIsClicked] = useState(false);
  const [fixedUrl, setFixedUrl] = useState("");
  const { searchCriteria, reset } = useSearchCriteriaStore();
  const { selectedCodeReviewId } = useCodeReviewsForCodeUpdateSelection();
  const codeQuery = useCode({
    githubUrl: convertGitHubUrl(watch("githubUrl")),
    config: { enabled: false },
  });
  const dynamicRoute = router.asPath;

  // const [submitBtnDisabled, setSubmitBtnDisabled] = useState(true);
  useEffect(() => {
    setValue("codeId", codeId);
    setValue("projectId", null);
    setValue("selectedReviewsId", selectedCodeReviewId);
  }, [setValue, codeId, selectedCodeReviewId]);

  useEffect(() => {
    setValue(
      "selectedTagsId",
      searchCriteria.tagIdList.map((el) => el.tagId)
    );
  }, [setValue, searchCriteria]);

  useEffect(() => {
    if (codeUpdateInitialValues) {
      setValue("title", codeUpdateInitialValues.title, {
        shouldDirty: true,
        shouldValidate: true,
      });
      setValue("githubUrl", codeUpdateInitialValues.githubUrl, {
        shouldDirty: true,
        shouldValidate: true,
      });
      setValue("content", codeUpdateInitialValues.content, {
        shouldValidate: true,
        shouldDirty: true,
      });
      setValue("projectId", codeUpdateInitialValues.projectId, {
        shouldValidate: true,
        shouldDirty: true,
      });
      setValue("selectedTagsId", codeUpdateInitialValues.selectedTagsId, {
        shouldValidate: true,
        shouldDirty: true,
      });
      setValue("selectedReviewsId", codeUpdateInitialValues.selectedReviewsId, {
        shouldValidate: true,
        shouldDirty: true,
      });
      setValue("language", codeUpdateInitialValues.language, {
        shouldValidate: true,
        shouldDirty: true,
      });
    }
  }, []);

  useEffect(() => reset(), [dynamicRoute, reset]);

  register("codeId");
  register("projectId");
  register("selectedTagsId");
  register("selectedReviewsId");
  register("language");

  const onGitHubBtnClick = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();
    setIsClicked(true);
    const eventTarget = e.target as HTMLButtonElement;
    eventTarget.disabled = true;
    const query = await codeQuery.refetch();

    if (query?.status === "success") {
      setIsValidUrl(true);
      setFixedUrl(watch("githubUrl"));
    }
    if (query?.status === "error") {
      eventTarget.disabled = false;
      setIsValidUrl(false);
    }
  };
  // const getLanguageName = async (id: number) => {
  //   const tags = await getLanguageTags({
  //     whichTag: "language",
  //     tagKeyword: "",
  //   });
  //   return tags.filter((el) => el.languageId === id)[0].name;
  // };

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
      <FlexDiv direction="column" align="flex-start" width="100%" gap="0.2rem">
        <FlexDiv direction="row" justify="flex-start" width="100%" gap="2rem">
          <InputField
            type="text"
            label="GitHub URL"
            isDirty={dirtyFields["githubUrl"]}
            error={errors["githubUrl"]}
            registration={register("githubUrl")}
            inputContainerWidth="80%"
          />

          <GitHubBtn
            onClick={onGitHubBtnClick}
            disabled={watch("githubUrl") === "" || Boolean(errors.githubUrl)}
          >
            불러오기
          </GitHubBtn>
        </FlexDiv>
        {isValidUrl ? (
          <InputSuccessMsg>
            성공! 코드를 불러오는 데 성공했어요.
          </InputSuccessMsg>
        ) : isClicked && !isValidUrl ? (
          <InputErrorMsg>링크를 다시 확인해 주세요.</InputErrorMsg>
        ) : (
          <></>
        )}
      </FlexDiv>

      <TagSearch whichTag="code" />
      <LanguageSearch whichTag="language" />
      {codeQuery.data ? (
        <CodeEditor
          key="new"
          headerText="GitHub에서 불러온 코드입니다."
          lineSelection={false}
          height="30rem"
          // language={getLanguageName(watch("language"))}
          language="plaintext"
          originalCode={codeQuery.data.content || ""}
          noShad={true}
        />
      ) : (
        <CodeEditor
          key="old"
          headerText="GitHub URL을 입력하고 불러오기를 누르세요"
          lineSelection={false}
          height="30rem"
          // language={getLanguageName(watch("language"))}
          language="plaintext"
          originalCode={""}
          noShad={true}
        />
      )}
      <SubHeading>상세 설명</SubHeading>
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
          disabled={!isValid || !isValidUrl}
        >
          확인
        </SubmitButton>
      </SubmitButtonWrapper>
    </>
  );
};
