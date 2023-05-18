import dayjs from "dayjs";
import { useRouter } from "next/router";
import { useState, Dispatch, SetStateAction } from "react";

import {
  Count,
  FlexDiv,
  Nickname,
  Text,
  WhiteBoxShad,
  Selection,
  Modal,
} from "@/components/elements";
import { useCodeReviewFeedbacks } from "@/features/feedbacks/api";
import { queryClient } from "@/lib/react-query";

import {
  useDeleteCodeReview,
  usePutCodeReviewComplaint,
  usePostCodeReviewLike,
} from "../../api";
import { Review } from "../../types";
import { DeleteCodeReview } from "./delete-code-review";
import { ReviewItemWrapper } from "./style";

interface CodeReviewItemPops {
  review: Review;
  isMine: boolean;
  showDetails: boolean;
  codeId: number;
  setClickedReviewId: Dispatch<SetStateAction<number>>;
}

export const CodeReviewItem = ({
  review: {
    reviewId,
    userId,
    userName,
    codeContent,
    content,
    language,
    lineNumbers,
    likeCnt,
    selected,
    liked,
    date,
    reReviews,
  },
  isMine,
  showDetails,
  codeId,
  setClickedReviewId,
}: CodeReviewItemPops) => {
  const router = useRouter();

  // selection 선택시 닫기 위한 state
  const [forceClose, setForceClose] = useState(false);

  // 리뷰 선택시 정보 가져오기
  const { refetch } = useCodeReviewFeedbacks(reviewId);

  const hadleReviewClick = () => {
    // 신고된 리뷰이면 클릭 안 됨
    if (!content.length) return;

    refetch();
    setClickedReviewId(reviewId);
  };

  const [codeReviewDeleteModalOpen, setCodeReviewDeleteModalOpen] =
    useState(false);

  // 코드 리뷰 좋아요/좋아요 취소
  const postCodeReviewLikeQuery = usePostCodeReviewLike();
  const handleLike = () => {
    postCodeReviewLikeQuery.mutate(reviewId, {
      onSuccess: () => queryClient.invalidateQueries(["codeInfo", codeId]),
    });
  };

  // 코드 리뷰 삭제하기
  const deleteCodeReviewQuery = useDeleteCodeReview();
  const handleDelete = () => {
    deleteCodeReviewQuery.mutate(reviewId);
    queryClient.invalidateQueries(["codeInfo", codeId]);
    setCodeReviewDeleteModalOpen(false);
  };

  // 코드 리뷰 신고하기
  const putCodeReviewComplaint = usePutCodeReviewComplaint();
  const handleCodeReviewComplaint = () => {
    putCodeReviewComplaint.mutate(reviewId, {
      onSettled: () => setForceClose(true),
      onSuccess: () => {
        queryClient.invalidateQueries(["codeInfo", codeId]);
        alert("신고하였습니다");
      },
    });
    setForceClose(false);
  };

  // 코드 리뷰 selection list
  const selectionListMine = {
    수정하기: () => router.push(`/code-review/review/modify/${reviewId}`),
    삭제하기: () => setCodeReviewDeleteModalOpen(true),
  };
  const selectionListNotMine = {
    신고하기: () => handleCodeReviewComplaint(),
  };

  return (
    <>
      <ReviewItemWrapper
        shadColor={showDetails ? "main" : "shad"}
        onClick={hadleReviewClick}
        width="85%"
        margin="0.5rem 0"
        isComplainted={Boolean(!content.length)}
      >
        <FlexDiv
          direction="column"
          padding="1rem"
          pointer={true}
          width="100%"
          gap="1rem"
        >
          <FlexDiv width="100%" justify="space-between" pointer={true}>
            <Nickname nickname={!userName.length ? "익명" : userName} />
            {content.length ? (
              <FlexDiv pointer={true}>
                <Count
                  type="like"
                  isChecked={liked}
                  cnt={likeCnt}
                  onClickFunc={handleLike}
                />
                <Selection
                  selectionList={
                    isMine ? selectionListMine : selectionListNotMine
                  }
                  forceClose={forceClose}
                />
              </FlexDiv>
            ) : (
              <Text color="red" bold={true} lineHeight="1.8rem">
                신고됨
              </Text>
            )}
          </FlexDiv>

          <FlexDiv direction="row-reverse" justify="space-between" width="100%">
            <Text size="0.8rem">{dayjs(date).format("YY.MM.DD HH:MM")}</Text>
            {Boolean(selected) && (
              <Text color="main" bold={true}>
                코드에 반영됨
              </Text>
            )}
          </FlexDiv>
        </FlexDiv>
      </ReviewItemWrapper>

      <Modal
        headerText={"코드 리뷰 삭제"}
        height="10rem"
        isOpen={codeReviewDeleteModalOpen}
        setIsOpen={setCodeReviewDeleteModalOpen}
        setForceClose={setForceClose}
        modalContent={
          <DeleteCodeReview
            setModalOpen={setCodeReviewDeleteModalOpen}
            handleDelete={handleDelete}
            setForceClose={setForceClose}
          />
        }
      />
    </>
  );
};
