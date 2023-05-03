import { FlexDiv } from "@/components/elements";
import { Feedback } from "@/features/projects/types";

import { FeedbackItem } from "./feedback-item";

interface FeedbacksProps {
  feedbacks: Feedback[];
  type: "feedback" | "review";
}

export const Feedbacks = ({ feedbacks, type }: FeedbacksProps) => {
  return (
    <FlexDiv direction="column" width="65%" gap="1rem">
      {feedbacks.length !== 0 &&
        feedbacks.map((feedback, idx) => (
          <FeedbackItem feedback={feedback} type={type} key={idx} />
        ))}
    </FlexDiv>
  );
};
