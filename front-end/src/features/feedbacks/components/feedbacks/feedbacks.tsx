import { FlexDiv } from "@/components/elements";
import { useUser } from "@/features/auth";
import { ProjectFeedback } from "@/features/projects/types";
import { CodeReviewFeedback } from "@/features/code-reviews";

import { FeedbackItem } from "./feedback-item";

interface FeedbacksProps {
  feedbacks: ProjectFeedback[] | CodeReviewFeedback[];
  type: "project" | "review";
  projectOrCodeId: number;
  clickedReviewId?: number;
}

export const Feedbacks = ({
  feedbacks,
  type,
  projectOrCodeId,
  clickedReviewId,
}: FeedbacksProps) => {
  // 현재 로그인한 유저 정보(피드백이 본인 것인지 확인하기 위함)
  const userQuery = useUser();

  return (
    <FlexDiv direction="column" width="60%" minWidth="36rem" gap="1rem">
      {feedbacks.length !== 0 &&
        feedbacks.map((feedback, idx) => (
          <FeedbackItem
            feedback={feedback}
            type={type}
            projectOrCodeId={projectOrCodeId}
            // 익명이 아니고 로그인한 유저 닉네임과 피드백의 유저네임이 같을 때
            isMine={Boolean(
              feedback.userName.length !== 0 &&
                userQuery.data?.nickname === feedback.userName
            )}
            clickedReviewId={clickedReviewId}
            key={`${feedback.content} + ${idx}`}
          />
        ))}
    </FlexDiv>
  );
};
