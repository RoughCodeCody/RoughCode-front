import { useEffect, useState } from "react";

import { FlexDiv, LoadingSpinner, WhiteBoxNoshad } from "@/components/elements";
import { CodeEditor, DiffCodeEditor } from "@/features/code-editor";
import { FeedbackRegister, Feedbacks } from "@/features/feedbacks";
import { useCodeReviewFeedbacks } from "@/features/feedbacks/api";
import { VersionsInfo } from "@/features/version-info";

import { useCodeInfo, useCode } from "../api";
import { CodeDescription } from "../components/code-description";
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
  const githubApiUrl = data?.githubApiUrl ? data?.githubApiUrl : "";
  const codeQuery = useCode({ githubApiUrl });
  const originalCode = codeQuery.data?.content;
  const language = data?.language
    ? data?.language
    : { languageId: 1, name: "plaintext", cnt: 1 };

  // 코드 리뷰 클릭시 해당 리뷰의 정보를 가져오기 위한 state
  // let defaultCilckedReviewId =
  // data && data.reviews.length !== 0 ? data.reviews[0].reviewId : -1;

  const [clickedReviewId, setClickedReviewId] = useState(-1);

  // 최초 렌더시 신고되지 않은 가장 앞의 리뷰를 선택
  useEffect(() => {
    if (data && data.reviews.length !== 0) {
      for (let i = 0; i < data.reviews.length; i++) {
        if (data.reviews[i].content.length) {
          setClickedReviewId(data.reviews[i].reviewId);
          break;
        }
      }
    }
    // setClickedReviewId(defaultCilckedReviewId);
  }, [status]);

  // 선택된 리뷰의 정보를 가져옴
  const codeReviewInfoQuery = useCodeReviewFeedbacks(clickedReviewId);

  return (
    <>
      <FlexDiv align="start" direction="row-reverse" justify="end" gap="1%">
        {data && (
          <CodeReviewList
            reviews={data.reviews}
            codeId={Number(codeId)}
            clickedReviewId={clickedReviewId}
            setClickedReviewId={setClickedReviewId}
          />
        )}

        <FlexDiv direction="column" gap="4rem" padding="2rem 0" width="60%">
          <WhiteBoxNoshad padding="2.25rem" style={{ minWidth: "850px" }}>
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

                <CodeDescription content={data.content} />

                {originalCode && (
                  <FlexDiv width="100%" height="100%" margin="2.5rem 0 0 0">
                    <CodeEditor
                      headerText="코드 리뷰를 요청한 원본 코드입니다"
                      lineSelection={false}
                      height="30rem"
                      language={language}
                      originalCode={originalCode || ""}
                      selectedLines={codeReviewInfoQuery.data?.lineNumbers}
                      noShad={true}
                    />
                  </FlexDiv>
                )}

                {originalCode && data.reviews.length !== 0 && (
                  <FlexDiv width="100%" height="100%" margin="2.5rem 0 0 0">
                    <DiffCodeEditor
                      headerText="코드 리뷰어가 수정한 코드입니다"
                      height="30rem"
                      readOnly={true}
                      language={language}
                      originalCode={originalCode}
                      modifiedCode={codeReviewInfoQuery.data?.codeContent || ""}
                      noShad={true}
                    />
                  </FlexDiv>
                )}

                {data.reviews.length !== 0 && (
                  <FlexDiv width="100%" height="100%" margin="2.5rem 0 0 0">
                    <ClickedReviewContent
                      content={
                        codeReviewInfoQuery.data?.content || "설명이 없습니다"
                      }
                    />
                  </FlexDiv>
                )}
              </>
            )}
          </WhiteBoxNoshad>

          {data && data.reviews.length !== 0 && (
            <>
              <FeedbackRegister
                type="review"
                id={clickedReviewId}
                codeId={Number(codeId)}
              />

              <Feedbacks
                type="review"
                feedbacks={codeReviewInfoQuery.data?.reReviews || []}
                projectOrCodeId={Number(codeId)}
                clickedReviewId={clickedReviewId}
              />
            </>
          )}
        </FlexDiv>
      </FlexDiv>

      {/* <LoadingSpinner isOpen={Boolean(status === "loading")} /> */}
    </>
  );
};
