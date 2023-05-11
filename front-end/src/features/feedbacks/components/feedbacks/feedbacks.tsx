import { FlexDiv } from "@/components/elements";
import { useUser } from "@/features/auth";
import { Feedback } from "@/features/projects/types";

import { FeedbackItem } from "./feedback-item";

interface FeedbacksProps {
  feedbacks: Feedback[];
  type: "feedback" | "review";
  projectOrCodeid: string;
}

export const Feedbacks = ({
  feedbacks,
  type,
  projectOrCodeid,
}: FeedbacksProps) => {
  // 현재 로그인한 유저 정보(피드백이 본인 것인지 확인하기 위함)
  const userQuery = useUser();

  return (
    <FlexDiv direction="column" width="65%" gap="1rem">
      {feedbacks.length !== 0 &&
        feedbacks.map((feedback, idx) => (
          <FeedbackItem
            feedback={feedback}
            type={type}
            projectOrCodeid={projectOrCodeid}
            // 익명이 아니고 로그인한 유저 닉네임과 피드백의 유저네임이 같을 때
            isMine={Boolean(
              feedback.userName.length !== 0 &&
                userQuery.data?.nickname === feedback.userName
            )}
            key={idx}
          />
        ))}
    </FlexDiv>
  );
};
