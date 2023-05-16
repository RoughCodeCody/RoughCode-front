import { useQueryClient } from "@tanstack/react-query";
import { useState } from "react";

import { FlexDiv, Text, WhiteBoxShad, Btn } from "@/components/elements";

import { usePostCodeReviewFeedback, usePostProjectFeedback } from "../../api";
import { FeedbackRegisterTextarea } from "./style";

type FeedbackRegisterProps = {
  type: "project" | "review";
  id: number;
};

export const FeedbackRegister = ({ type, id }: FeedbackRegisterProps) => {
  const queryClient = useQueryClient();
  const [content, setContent] = useState("");
  const postProjectFeedbackQuery = usePostProjectFeedback();
  const postCodeReviewFeedbackQuery = usePostCodeReviewFeedback();

  // 피드백 등록
  const postFeedback = () => {
    if (!content.length) return;

    if (type === "project") {
      postProjectFeedbackQuery.mutate(
        {
          projectId: id,
          content,
        },
        {
          onSuccess: (_, newFeedback) =>
            queryClient.invalidateQueries([
              "projectInfo",
              newFeedback.projectId,
            ]),
          onSettled: () => setContent(""),
        }
      );
    } else if (type === "review") {
      postCodeReviewFeedbackQuery.mutate(
        { id, content },
        {
          onSuccess: (_, newFeedback) =>
            queryClient.invalidateQueries(["projectInfo", newFeedback.id]),
          onSettled: () => setContent(""),
        }
      );
    }
  };

  return (
    <FlexDiv direction="column" width="80%" minWidth="850px">
      <Text color="main" bold={true} padding="1rem 0" size="1.2rem">
        {type === "project"
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
              type === "project"
                ? "프로젝트에 제안할 점, 개선 의견, 발견한 버그 등을 피드백으로 남겨주세요"
                : "이 코드 리뷰에 대한 건설적인 의견을 자유롭게 남겨주세요"
            }
            value={content}
            onChange={(e) => setContent(e.target.value)}
            onKeyUp={(e) => {
              if (e.key === "Enter" && !e.shiftKey) postFeedback();
            }}
          />
          <Btn text="등록하기" padding="2rem 1rem" onClickFunc={postFeedback} />
        </FlexDiv>
      </WhiteBoxShad>
    </FlexDiv>
  );
};
