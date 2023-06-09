import { useRouter } from "next/navigation";
import { useState } from "react";

import { useCode, useCodeReviewInfo, useModifyCodeReview } from "../api";
import { CodeInfo } from "../components/code-info";
import { BottomHeader, Btn, FlexDiv, Title, Text } from "@/components/elements";
import { CodeEditor, DiffCodeEditor } from "@/features/code-editor";
import { useCodeReviewFeedbackDataStore } from "@/stores/code-review-feedback";
import { TiptapEditor } from "@/features/rich-text-editor/components/tiptap-editor";

type ModifyCodeReviewProps = {
  codeReviewId: number;
};

export const ModifyCodeReview = ({ codeReviewId }: ModifyCodeReviewProps) => {
  const router = useRouter();
  const [feedbackContent, setFeedbackContent] = useState("");

  const codeFeedbackQuery = useModifyCodeReview();
  const { CodeReviewFeedbackData, reset } = useCodeReviewFeedbackDataStore();

  // 여기서 정보 조회하고 하위 컴포넌트에 정보를 prop줌
  const codeFeedbackInfoQuery = useCodeReviewInfo(codeReviewId);

  const githubApiUrl = codeFeedbackInfoQuery.data?.githubApiUrl
    ? codeFeedbackInfoQuery.data?.githubApiUrl
    : "";
  const language = codeFeedbackInfoQuery.data?.language
    ? codeFeedbackInfoQuery.data?.language
    : { languageId: 1, name: "plaintext", cnt: 1 };
  const codeQuery = useCode({ githubApiUrl });
  const originalCode = codeQuery.data?.content;

  const handleEditorChange = (content: string) => {
    setFeedbackContent(content);
  };

  // 여기서 put 요청 보냄
  // 만들고 수정해야 함
  const putCodeFeedback = () => {
    if (
      Object.values(CodeReviewFeedbackData.isCompleted).every(
        (value) => value === true
      )
    ) {
      if (codeFeedbackInfoQuery.data) {
        const codeId = codeFeedbackInfoQuery.data?.code.codeId;
        const feedbackId = codeFeedbackInfoQuery.data?.reviewId;
        const data = {
          codeId: codeId,
          selectedRange: CodeReviewFeedbackData.selectedLines,
          codeContent: CodeReviewFeedbackData.modifiedCode,
          content: feedbackContent,
          // content: CodeReviewFeedbackData.feedbackContent,
        };
        codeFeedbackQuery.mutate(
          { data, codeReviewId },
          {
            onSuccess() {
              reset();
              router.push(`/code-review/${codeId}`);
            },
          }
        );
      }
    } else {
      // 코드 수정이 완료되지 않았습니다 라는 알림
      alert("수정 다 안됐음!");
    }
  };

  if (codeFeedbackInfoQuery.isLoading) {
    return <div></div>;
  }
  if (codeFeedbackInfoQuery.isError)
    return (
      <FlexDiv>
        <Text>Not Found</Text>
      </FlexDiv>
    );
  return (
    <FlexDiv width="100vw" direction="column" gap="8rem">
      <BottomHeader locations={["코드 피드백 수정"]} />
      <FlexDiv
        width="100%"
        maxWidth="1080px"
        direction="column"
        gap="6rem"
        align="center"
      >
        <FlexDiv width="95%">
          <CodeInfo data={codeFeedbackInfoQuery.data?.code} />
        </FlexDiv>
        <FlexDiv width="100%" direction="column" gap="3rem">
          <Title
            title="코드 수정"
            description="요청한 코드를 기반으로 수정 및 리뷰를 해주세요"
          />
          {originalCode ? (
            <>
              <FlexDiv width="100%" height="100%">
                <CodeEditor
                  // 코드 라인 선택 기능 버튼 여부
                  headerText="피드백할 라인들을 드래그 한 후 선택 버튼을 눌러주세요"
                  lineSelection={true}
                  height="30rem"
                  language={language}
                  originalCode={originalCode}
                  selectedLines={codeFeedbackInfoQuery.data.lineNumbers}
                />
              </FlexDiv>
              <FlexDiv width="100%" height="100%">
                <DiffCodeEditor
                  headerText="코드를 수정해 주세요"
                  height="30rem"
                  readOnly={false}
                  language={language}
                  originalCode={originalCode}
                  modifiedCode={codeFeedbackInfoQuery.data.codeContent}
                />
              </FlexDiv>
            </>
          ) : (
            // 스피너
            <></>
          )}
        </FlexDiv>
      </FlexDiv>
      <FlexDiv
        width="100%"
        maxWidth="1080px"
        direction="column"
        gap="2rem"
        align="center"
      >
        <Title
          title="리뷰 작성"
          description="코드에 대한 리뷰를 작성해 주세요"
        />
        {/* 여기가 md에디터 자리 */}
      </FlexDiv>
      <TiptapEditor onChange={handleEditorChange} minHeight="30rem" />

      {/* 등록 버튼 : 서버에 put 요청*/}
      <Btn
        text={"수정"}
        padding="0.6rem 1rem 0.6rem 1rem"
        fontSize="1.5rem"
        onClickFunc={() => {
          putCodeFeedback();
        }}
      />
    </FlexDiv>
  );
};
