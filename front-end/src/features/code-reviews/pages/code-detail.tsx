import { FlexDiv, Text, WhiteBoxNoshad } from "@/components/elements";
import { CodeEditor, DiffCodeEditor } from "@/features/code-editor";
import { FeedbackRegister, Feedbacks } from "@/features/feedbacks";
import { VersionsInfo } from "@/features/version-info";

import { useCodeInfo } from "../api/get-code-info";
import { CodeInfo } from "../components/code-info";
import { CodeReviewList } from "../components/review-list";
import { useCode } from "../api/get-code";

interface CodeDetailProps {
  codeId: string;
}

export const CodeDetail = ({ codeId }: CodeDetailProps) => {
  // 더미데이터
  const isMine = true;

  const { status, data } = useCodeInfo(Number(codeId));

  console.log(data);

  const githubUrl = data?.githubUrl ? data?.githubUrl : "";
  const codeQuery = useCode({ githubUrl });
  const originalCode = codeQuery.data?.content;

  return (
    <>
      <FlexDiv direction="column" gap="4rem" padding="2rem 0">
        <WhiteBoxNoshad
          width="65%"
          padding="2.25rem"
          style={{ minWidth: "850px" }}
        >
          {data && (
            <>
              <CodeInfo data={data} />
              <VersionsInfo
                versions={data.versions}
                curVersionId={codeId}
                isMine={isMine}
              />

              {originalCode && (
                <FlexDiv width="100%" height="100%" margin="2.5rem 0 0 0">
                  <CodeEditor
                    headerText="코드 리뷰를 요청한 원본 코드입니다"
                    lineSelection={false}
                    height="30rem"
                    language={"javascript"}
                    originalCode={originalCode}
                    noShad={true}
                  />
                </FlexDiv>
              )}

              <FlexDiv direction="column" width="100%">
                <Text color="main" bold={true} padding="1rem 0" size="1.2rem">
                  이 코드에 대한 코드 리뷰 목록
                </Text>
              </FlexDiv>

              {/* <FlexDiv width="100%" height="100%">
                    <DiffCodeEditor
                      headerText="코드 리뷰어가 수정한 코드입니다"
                      height="30rem"
                      readOnly={true}
                      language={"javascript"}
                      originalCode={originalCode}
                    />
                  </FlexDiv> */}

              <CodeReviewList />
            </>
          )}
        </WhiteBoxNoshad>

        {/* <FeedbackRegister type="review" id={} />
        <Feedbacks type="review" feedbacks={} /> */}
      </FlexDiv>
    </>
  );
};
