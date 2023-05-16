import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { CodeEditor, DiffCodeEditor } from "@/features/code-editor";
import { FeedbackRegister, Feedbacks } from "@/features/feedbacks";
import { VersionsInfo } from "@/features/version-info";
import { useClickedReviewStore } from "@/stores";

import { useCodeInfo, useCode } from "../api";
import { CodeInfo } from "../components/code-info";
import { ClickedReviewContent } from "../components/clicked-review-content";
import { CodeReviewList } from "../components/review-list";

interface CodeDetailProps {
  codeId: string;
}

export const CodeDetail = ({ codeId }: CodeDetailProps) => {
  // 코드 정보 가져오기
  const { status, data } = useCodeInfo(Number(codeId));

  console.log(data);

  // 깃허브 코드 내용 가져오기
  const githubUrl = data?.githubUrl ? data?.githubUrl : "";
  const codeQuery = useCode({ githubUrl });
  const originalCode = codeQuery.data?.content;

  // 현재 클릭되어 리리뷰를 보여주고 있는 리뷰 관련 스토어
  const { clickedReview } = useClickedReviewStore();

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
                    selectedLines={clickedReview.lineNumbers}
                    noShad={true}
                  />
                </FlexDiv>
              )}

              <FlexDiv width="100%" height="100%">
                <DiffCodeEditor
                  headerText="코드 리뷰어가 수정한 코드입니다"
                  height="30rem"
                  readOnly={true}
                  language={"javascript"}
                  originalCode={originalCode || ""}
                  modifiedCode={clickedReview.codeContent}
                />
              </FlexDiv>

              <CodeReviewList reviews={data.reviews} codeId={Number(codeId)} />
              <ClickedReviewContent content={clickedReview.content} />
            </>
          )}
        </WhiteBoxNoshad>

        <FeedbackRegister type="review" id={clickedReview.reviewId} />
        <Feedbacks
          type="review"
          feedbacks={clickedReview.reReviews}
          projectOrCodeId={Number(codeId)}
        />
      </FlexDiv>
    </>
  );
};
