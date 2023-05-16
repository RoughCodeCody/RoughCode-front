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
import {
  useDeleteProjectFeedback,
  usePostFeedbackLike,
  usePutFeedbackComplaint,
  usePutProjectFeedback,
} from "@/features/projects/api";
import { ProjectFeedback } from "@/features/projects/types";
import { CodeReviewFeedback } from "@/features/code-reviews";
import { queryClient } from "@/lib/react-query";

import { FeedbackItemWrapper, FeedbackModifyInput } from "./style";
import { usePostCodeReviewFeedbackLike } from "@/features/code-reviews/api/post-code-review-feedback-like";

interface FeedbackItemProps {
  feedback: ProjectFeedback | CodeReviewFeedback;
  type: "project" | "review";
  projectOrCodeId: number;
  isMine: boolean;
}

// export type ProjectFeedback = {
//   feedbackId: number;
//   userId: number; // 피드백 남긴 사람 id (0이면 익명)
//   userName: string; // 빈 문자열이면 익명
//   content: string; // 피드백 내용, 신고당해서 삭제된 피드백은 빈 문자열로 내보내집니다!!
//   like: number;
//   selected: number; // 선택 받은 횟수
//   liked: boolean; // 내가 좋아요 눌렀는지 여부
//   date: Date;
// };

// export type ReviewFeedback = {
//   reReviewId: number; // 코드 리리뷰(리뷰에 대한 리뷰) id
//   userId: number; // 코드 리리뷰 작성자 id (0이면 익명)
//   userName: string; // 리리뷰 남긴 사람 닉네임(빈 문자열이면 익명)
//   liked: boolean; // 내가 좋아요 눌렀는지 여부
//   like: number; // 코드 리리뷰 좋아요 수
//   content: string; // 리리뷰 내용
//   date: Date
// };

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
}: FeedbackItemProps) => {
  // 피드백 신고 관련 state
  const [forceClose, setForceClose] = useState(false);

  // 피드백 수정 관련 state
  const [isModifying, setIsModifying] = useState(false);
  const [newContent, setNewContent] = useState(feedback.content);
  const modifyInputRef = useRef<HTMLInputElement | null>(null);

  const invalidateQuery = () => {
    const queryKey = type === "project" ? "projectInfo" : "codeInfo";
    queryClient.invalidateQueries([queryKey, projectOrCodeId]);
  };

  // 피드백 좋아요/좋아요 취소
  const postProjectFeedbackLikeQuery = usePostFeedbackLike();
  const postCodeReviewFeedbackLikeQuery = usePostCodeReviewFeedbackLike();
  const handleFeedbackLike = () => {
    if (isProject(feedback)) {
      postProjectFeedbackLikeQuery.mutate(feedback.feedbackId, {
        onSuccess: invalidateQuery,
      });
    } else if (isCode(feedback)) {
      postCodeReviewFeedbackLikeQuery.mutate(feedback.reReviewId, {
        onSuccess: invalidateQuery,
      });
    }
  };

  // 피드백 수정
  const putFeedbackQuery = usePutProjectFeedback();
  const handlePutFeedback = () => {
    if (isProject(feedback)) {
      putFeedbackQuery.mutate(
        { feedbackId: feedback.feedbackId, content: newContent },
        {
          onSuccess: () => {
            setIsModifying(false);
            invalidateQuery;
          },
        }
      );
    } else if (isCode(feedback)) {
    }
  };

  // 피드백 삭제
  const deleteFeedbackQuery = useDeleteProjectFeedback();
  const handleDeleteFeedback = () => {
    if (isProject(feedback)) {
      deleteFeedbackQuery.mutate(feedback.feedbackId, {
        onSuccess: invalidateQuery,
      });
    }
  };

  // 피드백 신고
  const putFeedbackComplaintQuery = usePutFeedbackComplaint();
  const handleFeedbackComplaint = () => {
    if (isProject(feedback)) {
      putFeedbackComplaintQuery.mutate(feedback.feedbackId, {
        onSettled: () => setForceClose(true),
        onSuccess: () => {
          invalidateQuery;
          alert("신고하였습니다");
        },
      });
      setForceClose(false);
    }
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
            <FeedbackModifyInput
              value={newContent}
              ref={modifyInputRef}
              onChange={(e) => setNewContent(e.target.value)}
            />

            <Btn text="수정 완료" onClickFunc={handlePutFeedback} />
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
                  onClickFunc={handleFeedbackLike}
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
            <Text color={!feedback.content.length ? "red" : "font"}>
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
