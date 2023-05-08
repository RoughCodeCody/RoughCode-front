import { useCodeInfo } from "../api/get-code-info";
import { useCode } from "../api/get-code";
import { CodeInfo } from "../components/code-info";
import { BottomHeader, Btn, FlexDiv, Title, Text } from "@/components/elements";
import { CodeEditor, DiffCodeEditor } from "@/features/code-editor";
import { useCodeReviewFeedbackDataStore } from "@/stores/code-review-feedback";

type CreateFeedbackProps = {
  codeId: number;
};

export const CreateFeedback = ({ codeId }: CreateFeedbackProps) => {
  const { CodeReviewFeedbackData } = useCodeReviewFeedbackDataStore();

  // 여기서 정보 조회하고 하위 컴포넌트에 정보를 prop줌
  const codeInfoQuery = useCodeInfo(codeId);

  const githubUrl = codeInfoQuery.data?.githubUrl
    ? codeInfoQuery.data?.githubUrl
    : "";
  const codeQuery = useCode({ githubUrl });
  const originalCode = codeQuery.data?.content;

  // 여기서 post 요청 보냄
  const sendData = () => {
    console.log(CodeReviewFeedbackData.selectedLines);
    console.log(CodeReviewFeedbackData.modifiedCode);
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
                  language="javascript"
                  originalCode={originalCode}
                />
              </FlexDiv>
              <FlexDiv width="100%" height="100%">
                <DiffCodeEditor
                  headerText="코드를 수정해 주세요"
                  height="30rem"
                  readOnly={false}
                  language="javascript"
                  originalCode={originalCode}
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

      {/* 등록 버튼 : 서버에 post 요청*/}
      <Btn
        text={"등록"}
        fontSize="2rem"
        onClickFunc={() => {
          sendData();
        }}
      />
    </FlexDiv>
  );
};
