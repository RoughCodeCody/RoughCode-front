import { useEffect } from "react";

import { FlexDiv, Text } from "@/components/elements";
import { useUser } from "@/features/auth";
import { useClickedReviewStore } from "@/stores";

import { Review } from "../../types";
import { CodeReviewItem } from "./review-item";

interface CodeReviewListProps {
  reviews: Review[];
}

export const CodeReviewList = ({ reviews }: CodeReviewListProps) => {
  // 현재 로그인한 유저 정보(피드백이 본인 것인지 확인하기 위함)
  const userQuery = useUser();

  // 현재 클릭되어 리리뷰를 보여주고 있는 리뷰 관련 스토어
  const {
    initialState: { clickedReviewId },
    setClickedReviewId,
  } = useClickedReviewStore();

  useEffect(() => {
    if (reviews.length > 0) setClickedReviewId(reviews[0].reviewId);
  }, []);

  return (
    <>
      <FlexDiv direction="column" width="100%" gap="1rem" margin="4rem 0 0 0">
        <Text color="main" bold={true} padding="1rem 0" size="1.2rem">
          이 코드에 대한 코드 리뷰 목록
        </Text>

        {/* 검색을 만들게 되면 이 자리에 */}

        {reviews.length !== 0 &&
          reviews.map((review) => (
            <>
              <CodeReviewItem
                review={review}
                // 익명이 아니고 로그인한 유저 닉네임과 리뷰의 유저네임이 같을 때
                isMine={Boolean(
                  review.userName.length !== 0 &&
                    userQuery.data?.nickname === review.userName
                )}
                showDetails={Boolean(review.reviewId === clickedReviewId)}
                key={review.reviewId}
              />
            </>
          ))}
      </FlexDiv>
    </>
  );
};
