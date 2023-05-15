import dayjs from "dayjs";
import { useState } from "react";

import {
  Count,
  FlexDiv,
  Nickname,
  Text,
  WhiteBoxShad,
  Selection,
} from "@/components/elements";
import { useClickedReviewStore } from "@/stores";

import { Review } from "../../types";
import { useCodeReviewFeedbacks } from "../../api/get-code-review-feedbacks";

interface CodeReviewItem {
  review: Review;
  isMine: boolean;
  showDetails: boolean;
}

export const CodeReviewItem = ({
  review: {
    reviewId,
    userId,
    userName,
    codeContent,
    content,
    language,
    lineNumbers,
    likeCnt,
    selected,
    liked,
    date,
    reReviews,
  },
  isMine,
  showDetails,
}: CodeReviewItem) => {
  // selection 선택시 닫기 위한 state
  const [forceClose, setForceClose] = useState(false);

  // 리뷰 선택시 정보 가져오기
  const { status, data, refetch } = useCodeReviewFeedbacks({
    reviewId,
    config: { enabled: false, refetchOnWindowFocus: false },
  });

  const { setClickedReviewInfo } = useClickedReviewStore();

  const hadleReviewClick = () => {
    refetch();
    if (status === "success") {
      const { reviewId, codeContent, lineNumbers, reReviews } = data;
      setClickedReviewInfo({ reviewId, codeContent, lineNumbers, reReviews });
    }
  };

  const selectionListMine = { 수정하기: () => {}, 삭제하기: () => {} };
  const selectionListNotMine = { 신고하기: () => {} };

  return (
    <WhiteBoxShad
      shadColor={showDetails ? "main" : "shad"}
      onClick={hadleReviewClick}
    >
      <FlexDiv direction="column" padding="1rem" pointer={true}>
        <FlexDiv width="100%" justify="space-between" pointer={true}>
          <FlexDiv>
            <Nickname nickname={!userName.length ? "익명" : userName} />
            <Text size="0.8rem" padding="0 2rem 0 1rem">
              {dayjs(date).format("YY.MM.DD HH:MM")}
            </Text>
            {Boolean(selected) && (
              <Text color="main" bold={true}>
                코드에 반영됨
              </Text>
            )}
          </FlexDiv>

          {!content.length || (
            <FlexDiv pointer={true}>
              <Count type="like" isChecked={liked} cnt={likeCnt} />
              <Selection
                selectionList={
                  isMine ? selectionListMine : selectionListNotMine
                }
                forceClose={forceClose}
              />
            </FlexDiv>
          )}
        </FlexDiv>

        <FlexDiv
          width="100%"
          justify="space-between"
          align="end"
          pointer={true}
          style={{ whiteSpace: "pre", marginTop: "0.5rem" }}
        >
          {!content.length ? (
            <Text color={!content.length ? "red" : "font"}>
              신고되어 가려진 게시물입니다.
            </Text>
          ) : (
            <div
              dangerouslySetInnerHTML={{ __html: content }}
              style={{ width: "100%" }}
            ></div>
          )}
        </FlexDiv>
      </FlexDiv>
    </WhiteBoxShad>
  );
};
