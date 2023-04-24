import { FlexDiv } from "@/components/elements";
import { FeedbackItem } from "./feedback-item";

interface FeedbacksProps {
  feedbacks: {
    user: string;
    isApplied: boolean;
    content: string;
    isMine: boolean;
    isLiked: boolean;
    likeCnt: number;
    createdAt: string;
  }[];
}

export const Feedbacks = ({ feedbacks }: FeedbacksProps) => {
  return (
    <FlexDiv direction="column" width="65%" gap="1rem">
      {feedbacks.length !== 0 &&
        feedbacks.map((feedback, idx) => (
          <FeedbackItem feedback={feedback} key={idx} />
        ))}
    </FlexDiv>
  );
};
