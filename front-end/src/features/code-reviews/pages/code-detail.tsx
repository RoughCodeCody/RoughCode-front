import { useEffect, useState } from "react";

import { FlexDiv, Spinner, WhiteBoxNoshad } from "@/components/elements";
import { CodeEditor, DiffCodeEditor } from "@/features/code-editor";
import { FeedbackRegister, Feedbacks } from "@/features/feedbacks";
import { useCodeReviewFeedbacks } from "@/features/feedbacks/api";
import { VersionsInfo } from "@/features/version-info";

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

  // 깃허브 코드 내용 가져오기
  const githubUrl = data?.githubUrl ? data?.githubUrl : "";
  const codeQuery = useCode({ githubUrl });
  const originalCode = codeQuery.data?.content;

  // 코드 리뷰 클릭시 해당 리뷰의 정보를 가져오기 위한 state
  const defaultCilckedReviewId =
    data && data.reviews.length !== 0 ? data.reviews[0].reviewId : -1;

  const [clickedReviewId, setClickedReviewId] = useState(
    defaultCilckedReviewId
  );

  // 최초 렌더시 가장 앞의 리뷰를 선택
  useEffect(
    () => setClickedReviewId(defaultCilckedReviewId),
    [defaultCilckedReviewId]
  );

  // 선택된 리뷰의 정보를 가져옴
  const codeReviewInfoQuery = useCodeReviewFeedbacks(clickedReviewId);

  return (
    <>
      {status === "loading" ? (
        <Spinner size={300} />
      ) : (
        <FlexDiv direction="column" gap="4rem" padding="2rem 0">
          <WhiteBoxNoshad
            width="60%"
            padding="2.25rem"
            style={{ minWidth: "850px" }}
          >
            {data && (
              <>
                <CodeInfo
                  data={data}
                  isMine={data.mine}
                  isLatest={Boolean(data.version === data.versions.length)}
                />
                <VersionsInfo
                  versions={data.versions}
                  curVersionId={codeId}
                  isMine={data.mine}
                />

                <FlexDiv width="100%" height="100%" margin="2.5rem 0 0 0">
                  <CodeEditor
                    headerText="코드 리뷰를 요청한 원본 코드입니다"
                    lineSelection={false}
                    height="30rem"
                    language={"javascript"}
                    originalCode={originalCode || ""}
                    selectedLines={codeReviewInfoQuery.data?.lineNumbers}
                    noShad={true}
                  />
                </FlexDiv>

                <FlexDiv width="100%" height="100%">
                  <DiffCodeEditor
                    headerText="코드 리뷰어가 수정한 코드입니다"
                    height="30rem"
                    readOnly={true}
                    language={"javascript"}
                    originalCode={originalCode || ""}
                    modifiedCode={codeReviewInfoQuery.data?.codeContent || ""}
                  />
                </FlexDiv>

                <CodeReviewList
                  reviews={data.reviews}
                  codeId={Number(codeId)}
                  clickedReviewId={clickedReviewId}
                  setClickedReviewId={setClickedReviewId}
                />

                <ClickedReviewContent
                  content={
                    codeReviewInfoQuery.data?.content || "설명이 없습니다"
                  }
                />
              </>
            )}
          </WhiteBoxNoshad>

          <FeedbackRegister type="review" id={clickedReviewId} />
          <Feedbacks
            type="review"
            feedbacks={codeReviewInfoQuery.data?.reReviews || []}
            projectOrCodeId={Number(codeId)}
          />
        </FlexDiv>
      )}
    </>
  );
};
