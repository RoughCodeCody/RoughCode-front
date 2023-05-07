import dayjs from "dayjs";
import { useState } from "react";

import { Count, FlexDiv, Nickname, Text } from "@/components/elements";
import { Selection } from "@/components/selection";
import {
  useDeleteProjectFeedback,
  usePostFeedbackLike,
  usePutFeedbackComplaint,
} from "@/features/projects/api";
import { Feedback } from "@/features/projects/types";
import { queryClient } from "@/lib/react-query";

import { FeedbackItemWrapper } from "./style";

interface FeedbackItemProps {
  feedback: Feedback;
  type: "feedback" | "review";
  projectId: string;
}

export const FeedbackItem = ({
  feedback: {
    feedbackId,
    userId,
    userName,
    content,
    like,
    selected,
    liked,
    date,
  },
  type,
  projectId,
}: FeedbackItemProps) => {
  // 더미데이터
  const isMine = false;
  // const [newIsLiked, setNewIsLiked] = useState<boolean>(liked);
  // const [newLikeCnt, setNewLikeCnt] = useState<number>(like);

  const invalidateProjectInfoQuery = () => {
    queryClient.invalidateQueries({
      queryKey: ["projectInfo", projectId],
    });
  };

  // 피드백 좋아요/좋아요 취소
  const postFeedbackLikeQuery = usePostFeedbackLike();
  const handleFeedbackLike = () => {
    postFeedbackLikeQuery.mutate(feedbackId, {
      onSuccess: invalidateProjectInfoQuery,
    });
  };

  // 피드백 삭제
  const deleteFeedbackQuery = useDeleteProjectFeedback();
  const handleDeleteFeedback = () => {
    deleteFeedbackQuery.mutate(feedbackId, {
      onSuccess: invalidateProjectInfoQuery,
    });
  };

  // 피드백 신고
  const putFeedbackComplaintQuery = usePutFeedbackComplaint();
  const handleFeedbackComplaint = () => {
    putFeedbackComplaintQuery.mutate(feedbackId, {
      onSuccess: invalidateProjectInfoQuery,
    });
  };

  return (
    <FeedbackItemWrapper
      bgColor={Boolean(selected) ? "sub-one" : "white"}
      isMine={isMine}
    >
      <FlexDiv width="100%" justify="space-between">
        <FlexDiv>
          <Nickname nickname={!userName.length ? "익명" : userName} />
          {Boolean(selected) && (
            <Text color="main" bold={true} padding="0 2rem">
              {`${type === "feedback" ? "프로젝트" : "코드"}에 반영됨`}
            </Text>
          )}
        </FlexDiv>
        <FlexDiv>
          <Count
            type="like"
            isChecked={liked}
            // setIsChecked={setNewIsLiked}
            cnt={like}
            // setCnt={setNewLikeCnt}
            onClickFunc={handleFeedbackLike}
          />
          <Selection
            isMine={isMine}
            handleDeleteFunc={handleDeleteFeedback}
            handleComplaintFunc={handleFeedbackComplaint}
          />
        </FlexDiv>
      </FlexDiv>

      <FlexDiv width="100%" justify="space-between">
        <Text>
          {!content.length ? "신고되어 가려진 게시물입니다." : content}
        </Text>
        <Text size="0.8rem">{dayjs(date).format("YY.MM.DD HH:MM")}</Text>
      </FlexDiv>
    </FeedbackItemWrapper>
  );
};
