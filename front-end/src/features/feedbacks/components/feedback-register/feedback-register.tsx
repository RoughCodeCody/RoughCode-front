import { useQueryClient } from "@tanstack/react-query";
import { useState } from "react";

import { FlexDiv, Text, WhiteBoxShad, Btn } from "@/components/elements";
import { usePostProjectFeedback } from "@/features/projects/api";

import { FeedbackRegisterTextarea } from "./style";

type FeedbackRegisterProps = {
  type: "feedback" | "review";
  id: string;
};

export const FeedbackRegister = ({ type, id }: FeedbackRegisterProps) => {
  const queryClient = useQueryClient();
  const [content, setContent] = useState("");
  const postProjectFeedbackQuery = usePostProjectFeedback();

  // 프로젝트 피드백 등록
  const postProjectFeedback = () => {
    if (!content.length) return;

    postProjectFeedbackQuery.mutate(
      {
        projectId: id,
        content,
      },
      {
        onSuccess: (_, newFeedback) =>
          queryClient.invalidateQueries({
            queryKey: ["projectInfo", newFeedback.projectId],
          }),
        onSettled: () => setContent(""),
      }
    );
  };

  const postCodeReviewOpinion = () => {};

  return (
    <FlexDiv direction="column" width="80%" minWidth="850px">
      <Text color="main" bold={true} padding="1rem 0" size="1.2rem">
        {type === "feedback"
          ? "이 프로젝트에 피드백을 남겨주세요!"
          : "이 코드 리뷰에 의견을 남겨주세요!"}
      </Text>
      <WhiteBoxShad height="10rem" shadColor="main">
        <FlexDiv
          width="100%"
          height="100%"
          justify="space-between"
          padding="1.5rem"
        >
          <FeedbackRegisterTextarea
            placeholder={
              type === "feedback"
                ? "프로젝트에 제안할 점, 개선 의견, 발견한 버그 등을 피드백으로 남겨주세요"
                : "이 코드 리뷰에 대한 건설적인 의견을 자유롭게 남겨주세요"
            }
            value={content}
            onChange={(e) => setContent(e.target.value)}
          />
          <Btn
            text="등록하기"
            padding="2rem 1rem"
            onClickFunc={
              type === "feedback" ? postProjectFeedback : postCodeReviewOpinion
            }
          />
        </FlexDiv>
      </WhiteBoxShad>
    </FlexDiv>
  );
};
