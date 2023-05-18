import dayjs from "dayjs";
import { useEffect, useRef, useState } from "react";

import {
  Btn,
  Count,
  FlexDiv,
  Nickname,
  Text,
  Selection,
} from "@/components/elements";

import { ProjectFeedback } from "@/features/projects/types";
import { CodeReviewFeedback } from "@/features/code-reviews";
import { queryClient } from "@/lib/react-query";

import {
  useDeleteProjectFeedback,
  usePostProjectFeedbackLike,
  usePutProjectFeedbackComplaint,
  usePutProjectFeedback,
  useDeleteCodeReviewFeedback,
  usePostCodeReviewFeedbackLike,
  usePutCodeReviewFeedbackComplaint,
  usePutCodeReviewFeedback,
} from "../../api";
import { FeedbackItemWrapper, FeedbackModifyTextarea } from "./style";

interface FeedbackItemProps {
  feedback: ProjectFeedback | CodeReviewFeedback;
  type: "project" | "review";
  projectOrCodeId: number;
  isMine: boolean;
  clickedReviewId?: number;
}

// 프로젝트 버전인지 여부를 판별
function isProject(arg: any): arg is ProjectFeedback {
  return arg?.feedbackId !== undefined;
}

// 코드 버전인지 여부를 판별
function isCode(arg: any): arg is CodeReviewFeedback {
  return arg?.reReviewId !== undefined;
}

export const FeedbackItem = ({
  feedback,
  type,
  projectOrCodeId,
  isMine,
  clickedReviewId,
}: FeedbackItemProps) => {
  // 피드백 신고 관련 state
  const [forceClose, setForceClose] = useState(false);

  // 피드백 수정 관련 state
  const [isModifying, setIsModifying] = useState(false);
  const [newContent, setNewContent] = useState(feedback.content);
  const modifyInputRef = useRef<HTMLTextAreaElement | null>(null);

  const invalidateQuery = () => {
    if (type === "project") {
      queryClient.invalidateQueries(["projectInfo", projectOrCodeId]);
    } else if (type === "review") {
      Promise.all([
        queryClient.invalidateQueries(["codeInfo", projectOrCodeId]),
        queryClient.invalidateQueries(["codeReviewFeedbacks", clickedReviewId]),
      ]);
    }
  };

  // 피드백 좋아요/좋아요 취소
  const postProjectFeedbackLikeQuery = usePostProjectFeedbackLike();
  const postCodeReviewFeedbackLikeQuery = usePostCodeReviewFeedbackLike();
  const handleFeedbackLike = () => {
    if (isProject(feedback)) {
      postProjectFeedbackLikeQuery.mutate(feedback.feedbackId, {
        onSuccess: () => invalidateQuery(),
      });
    } else if (isCode(feedback)) {
      postCodeReviewFeedbackLikeQuery.mutate(feedback.reReviewId, {
        onSuccess: () => invalidateQuery(),
      });
    }
  };

  // 피드백 수정
  const putProjectFeedbackQuery = usePutProjectFeedback();
  const putReviewFeedbackQuery = usePutCodeReviewFeedback();
  const handlePutFeedback = () => {
    if (isProject(feedback)) {
      putProjectFeedbackQuery.mutate(
        { feedbackId: feedback.feedbackId, content: newContent },
        {
          onSuccess: () => {
            setIsModifying(false);
            invalidateQuery();
          },
        }
      );
    } else if (isCode(feedback)) {
      putReviewFeedbackQuery.mutate(
        { id: feedback.reReviewId, content: newContent },
        {
          onSuccess: () => {
            setIsModifying(false);
            invalidateQuery();
          },
        }
      );
    }
  };

  // 피드백 삭제
  const deleteProjectFeedbackQuery = useDeleteProjectFeedback();
  const deleteCodeReviewFeedbackQuery = useDeleteCodeReviewFeedback();
  const handleDeleteFeedback = () => {
    if (isProject(feedback)) {
      deleteProjectFeedbackQuery.mutate(feedback.feedbackId, {
        onSettled: () => setForceClose(true),
        onSuccess: () => invalidateQuery(),
      });
    } else if (isCode(feedback)) {
      deleteCodeReviewFeedbackQuery.mutate(feedback.reReviewId, {
        onSettled: () => setForceClose(true),
        onSuccess: () => invalidateQuery(),
      });
    }
    setForceClose(false);
  };

  // 피드백 신고
  const putProjectFeedbackComplaintQuery = usePutProjectFeedbackComplaint();
  const putReviewFeedbackComplaintQuery = usePutCodeReviewFeedbackComplaint();
  const handleFeedbackComplaint = () => {
    if (isProject(feedback)) {
      putProjectFeedbackComplaintQuery.mutate(feedback.feedbackId, {
        onSettled: () => setForceClose(true),
        onSuccess: () => {
          invalidateQuery();
          alert("신고하였습니다");
        },
      });
    } else if (isCode(feedback)) {
      putReviewFeedbackComplaintQuery.mutate(feedback.reReviewId, {
        onSettled: () => setForceClose(true),
        onSuccess: () => {
          invalidateQuery();
          alert("신고하였습니다");
        },
      });
    }
    setForceClose(false);
  };

  const selectionListMine = {
    수정하기: () => setIsModifying(true),
    삭제하기: handleDeleteFeedback,
  };

  const selectionListNotMine = {
    신고하기: handleFeedbackComplaint,
  };

  useEffect(() => {
    modifyInputRef.current?.focus();
  }, [isModifying]);

  return (
    <FeedbackItemWrapper
      bgColor={
        isProject(feedback) && Boolean(feedback.selected) ? "sub-one" : "white"
      }
      isMine={isMine}
    >
      {isModifying ? (
        <>
          <FlexDiv width="100%" justify="space-between">
            <FeedbackModifyTextarea
              value={newContent}
              defaultValue={feedback.content}
              ref={modifyInputRef}
              onChange={(e) => setNewContent(e.target.value)}
            />

            <Btn
              text="수정 완료"
              fontSize="0.8rem"
              margin="0 0.3rem"
              onClickFunc={handlePutFeedback}
            />
            <Btn
              text="취소"
              fontSize="0.8rem"
              bgColor="red"
              onClickFunc={() => setIsModifying(false)}
            />
          </FlexDiv>
        </>
      ) : (
        <>
          <FlexDiv width="100%" justify="space-between">
            <FlexDiv>
              <Nickname
                nickname={
                  !feedback.userName.length ? "익명" : feedback.userName
                }
              />
              {isProject(feedback) && Boolean(feedback.selected) && (
                <Text color="main" bold={true} padding="0 2rem">
                  프로젝트에 반영됨
                </Text>
              )}
            </FlexDiv>
            {!feedback.content.length || (
              <FlexDiv>
                <Count
                  type="like"
                  isChecked={feedback.liked}
                  cnt={feedback.like}
                  onClickFunc={(e) => {
                    e.stopPropagation();
                    handleFeedbackLike();
                  }}
                />
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
            style={{ whiteSpace: "pre", marginTop: "0.5rem" }}
          >
            <Text
              color={!feedback.content.length ? "red" : "font"}
              width="80%"
              whiteSpace="pre-wrap"
            >
              {!feedback.content.length
                ? "신고되어 가려진 게시물입니다."
                : feedback.content}
            </Text>
            <Text size="0.8rem">
              {dayjs(feedback.date).format("YY.MM.DD HH:MM")}
            </Text>
          </FlexDiv>
        </>
      )}
    </FeedbackItemWrapper>
  );
};
