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
  const [forceClose, setForceClose] = useState(false);

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
            {Boolean(selected) && (
              <Text color="main" bold={true} padding="0 2rem">
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
          <Text color={!content.length ? "red" : "font"}>
            {!content.length ? "신고되어 가려진 게시물입니다." : content}
          </Text>
          <Text size="0.8rem">{dayjs(date).format("YY.MM.DD HH:MM")}</Text>
        </FlexDiv>
      </FlexDiv>
    </WhiteBoxShad>
  );
};
