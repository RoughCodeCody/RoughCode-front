import dayjs from "dayjs";
import { useRef, useState } from "react";

import {
  Btn,
  Count,
  FlexDiv,
  Nickname,
  Text,
  Selection,
} from "@/components/elements";
import {
  useDeleteProjectFeedback,
  usePostFeedbackLike,
  usePutFeedbackComplaint,
  usePutProjectFeedback,
} from "@/features/projects/api";
import { Feedback } from "@/features/projects/types";
import { queryClient } from "@/lib/react-query";

import { FeedbackItemWrapper, FeedbackModifyInput } from "./style";

interface FeedbackItemProps {
  feedback: Feedback;
  type: "feedback" | "review";
  projectOrCodeid: string;
  isMine: boolean;
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
  projectOrCodeid,
  isMine,
}: FeedbackItemProps) => {
  // 피드백 수정 관련 state
  const [isModifying, setIsModifying] = useState(false);
  const [newContent, setNewContent] = useState(content);
  const ref = useRef<HTMLInputElement>(null);

  const invalidateProjectInfoQuery = () => {
    queryClient.invalidateQueries({
      queryKey: ["projectInfo", projectOrCodeid],
    });
  };

  // 피드백 좋아요/좋아요 취소
  const postFeedbackLikeQuery = usePostFeedbackLike();
  const handleFeedbackLike = () => {
    postFeedbackLikeQuery.mutate(feedbackId, {
      onSuccess: invalidateProjectInfoQuery,
    });
  };

  // 피드백 수정
  const putFeedbackQuery = usePutProjectFeedback();
  const handlePutFeedback = () => {
    putFeedbackQuery.mutate(
      { feedbackId, content: newContent },
      {
        onSuccess: invalidateProjectInfoQuery,
      }
    );
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

  const selectionListMine = {
    수정하기: () => {
      setIsModifying(true);
      if (ref.current) ref.current.focus();
    },
    삭제하기: handleDeleteFeedback,
  };

  const selectionListNotMine = {
    신고하기: handleFeedbackComplaint,
  };

  return (
    <FeedbackItemWrapper
      bgColor={Boolean(selected) ? "sub-one" : "white"}
      isMine={isMine}
    >
      {isModifying ? (
        <>
          <FlexDiv width="100%" justify="space-between">
            <FeedbackModifyInput
              value={newContent}
              ref={ref}
              onChange={(e) => setNewContent(e.target.value)}
            />

            <Btn text="수정 완료" onClickFunc={handlePutFeedback} />
          </FlexDiv>
        </>
      ) : (
        <>
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
                cnt={like}
                onClickFunc={handleFeedbackLike}
              />
              <Selection
                selectionList={
                  isMine ? selectionListMine : selectionListNotMine
                }
              />
            </FlexDiv>
          </FlexDiv>

          <FlexDiv width="100%" justify="space-between">
            <Text>
              {!content.length ? "신고되어 가려진 게시물입니다." : content}
            </Text>
            <Text size="0.8rem">{dayjs(date).format("YY.MM.DD HH:MM")}</Text>
          </FlexDiv>
        </>
      )}
    </FeedbackItemWrapper>
  );
};
