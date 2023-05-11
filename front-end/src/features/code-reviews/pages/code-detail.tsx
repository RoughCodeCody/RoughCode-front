import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { CodeEditor, DiffCodeEditor } from "@/features/code-editor";
import { FeedbackRegister, Feedbacks } from "@/features/feedbacks";
import { VersionsInfo } from "@/features/version-info";

import { useCodeInfo } from "../api/get-code-info";
import { useCode } from "../api/get-code";
import { CodeInfo } from "../components/code-info";
import { CodeReviewList } from "../components/review-list";

interface CodeDetailProps {
  codeId: string;
}

export const CodeDetail = ({ codeId }: CodeDetailProps) => {
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
                isMine={data.mine}
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

              <CodeReviewList reviews={data.reviews} />

              {/* <FlexDiv width="100%" height="100%">
                    <DiffCodeEditor
                      headerText="코드 리뷰어가 수정한 코드입니다"
                      height="30rem"
                      readOnly={true}
                      language={"javascript"}
                      originalCode={originalCode}
                    />
                  </FlexDiv> */}
            </>
          )}
        </WhiteBoxNoshad>

        {/* <FeedbackRegister type="review" id={} />
        <Feedbacks type="review" feedbacks={} /> */}
      </FlexDiv>
    </>
  );
};
