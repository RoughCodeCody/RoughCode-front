import { useRouter } from "next/navigation";
import { useState } from "react";

import { useCodeInfo, useCode, useCreateCodeReview } from "../api";
import { CodeInfo } from "../components/code-info";
import { BottomHeader, Btn, FlexDiv, Title, Text } from "@/components/elements";
import { CodeEditor, DiffCodeEditor } from "@/features/code-editor";
import { useCodeReviewFeedbackDataStore } from "@/stores/code-review-feedback";
import { TiptapEditor } from "@/features/rich-text-editor/components/tiptap-editor";

type CreateCodeFeedbackProps = {
  codeId: number;
};

export const CreateCodeFeedback = ({ codeId }: CreateCodeFeedbackProps) => {
  const router = useRouter();
  const [feedbackContent, setFeedbackContent] = useState("");
  const codeReviewQuery = useCreateCodeReview();
  const { CodeReviewFeedbackData, reset } = useCodeReviewFeedbackDataStore();

  // 여기서 정보 조회하고 하위 컴포넌트에 정보를 prop줌
  const codeInfoQuery = useCodeInfo(codeId);

  const githubApiUrl = codeInfoQuery.data?.githubApiUrl
    ? codeInfoQuery.data?.githubApiUrl
    : "";
  const language = codeInfoQuery.data?.language
    ? codeInfoQuery.data?.language
    : { tagId: 1, name: "plaintext", cnt: 1 };
  const codeQuery = useCode({ githubApiUrl });
  const originalCode = codeQuery.data?.content;

  const handleEditorChange = (content: string) => {
    setFeedbackContent(content);
    // do something with the content
  };
  // 여기서 post 요청 보냄
  const postCodeFeedback = () => {
    // editor 와 diffEditor 모두 완료된 상태이면
    if (
      Object.values(CodeReviewFeedbackData.isCompleted).every(
        (value) => value === true
      )
    ) {
      const data = {
        codeId: codeId,
        selectedRange: CodeReviewFeedbackData.selectedLines,
        codeContent: CodeReviewFeedbackData.modifiedCode,
        content: feedbackContent,
      };

      console.log(data);
      codeReviewQuery.mutate(
        { data },
        {
          onSuccess() {
            reset();
            router.push(`/code-review/${codeId}`);
          },
        }
      );
    } else {
      // 코드 수정이 완료되지 않았습니다 라는 알림
      alert("수정 다 안됐음!");
    }
  };

  if (codeInfoQuery.isLoading) {
    return <div></div>;
  }
  if (codeInfoQuery.isError)
    return (
      <FlexDiv>
        <Text>Not Found</Text>
      </FlexDiv>
    );
  return (
    <FlexDiv width="100vw" direction="column" gap="8rem">
      <FlexDiv
        width="100%"
        maxWidth="1080px"
        direction="column"
        gap="6rem"
        align="center"
      >
        <BottomHeader locations={["코드 피드백 등록"]} />
        <FlexDiv width="95%">
          <CodeInfo data={codeInfoQuery.data} />
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
                  language={{ languageId: 1, name: "plaintext", cnt: 1 }}
                  originalCode={originalCode}
                />
              </FlexDiv>
              <FlexDiv width="100%" height="100%">
                <DiffCodeEditor
                  headerText="코드를 수정해 주세요"
                  height="30rem"
                  readOnly={false}
                  language={{ languageId: 1, name: "plaintext", cnt: 1 }}
                  originalCode={originalCode}
                  modifiedCode={originalCode}
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
        <TiptapEditor onChange={handleEditorChange} minHeight="30rem" />
      </FlexDiv>

      {/* 등록 버튼 : 서버에 post 요청*/}
      <Btn
        text={"등록"}
        padding="0.6rem 1rem 0.6rem 1rem"
        fontSize="1.5rem"
        onClickFunc={() => {
          postCodeFeedback();
        }}
      />
    </FlexDiv>
  );
};
