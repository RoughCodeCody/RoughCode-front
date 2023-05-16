import dayjs from "dayjs";
import { useRouter } from "next/router";
import { useState } from "react";

import {
  Count,
  FlexDiv,
  Nickname,
  Text,
  WhiteBoxShad,
  Selection,
  Modal,
} from "@/components/elements";
import { queryClient } from "@/lib/react-query";
import { useClickedReviewStore } from "@/stores";

import { Review } from "../../types";
import { useCodeReviewFeedbacks } from "../../api/get-code-review-feedbacks";
import { useDeleteCodeReview } from "../../api/delete-code-review";
import { DeleteCodeReview } from "./delete-code-review";
import { usePutCodeReviewComplaint } from "../../api";

interface CodeReviewItem {
  review: Review;
  isMine: boolean;
  showDetails: boolean;
  codeId: number;
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
}: CodeReviewItem) => {
  const router = useRouter();

  // selection 선택시 닫기 위한 state
  const [forceClose, setForceClose] = useState(false);

  // 리뷰 선택시 정보 가져오기
  const { status, data, refetch } = useCodeReviewFeedbacks({
    reviewId,
    config: { enabled: false, refetchOnWindowFocus: false },
  });

  const { setClickedReviewInfo } = useClickedReviewStore();

  const hadleReviewClick = () => {
    refetch();
    if (status === "success") {
      const { reviewId, codeContent, lineNumbers, reReviews, content } = data;
      setClickedReviewInfo({
        reviewId,
        codeContent,
        lineNumbers,
        reReviews,
        content,
      });
    }
  };

  const [codeReviewDeleteModalOpen, setCodeReviewDeleteModalOpen] =
    useState(false);

  // 코드 리뷰 삭제하기
  const deleteCodeReviewQuery = useDeleteCodeReview();
  const handleDelete = () => {
    deleteCodeReviewQuery.mutate(reviewId);
    queryClient.invalidateQueries(["codeInfo", codeId]);
  };

  // 코드 리뷰 신고하기
  const putCodeReviewComplaint = usePutCodeReviewComplaint();

  const selectionListMine = {
    수정하기: () => router.push(`/code-review/review/modify/${reviewId}`),
    삭제하기: () => setCodeReviewDeleteModalOpen(true),
  };
  const selectionListNotMine = {
    신고하기: () => {
      putCodeReviewComplaint.mutate(reviewId);
      queryClient.invalidateQueries(["codeInfo", codeId]);
    },
  };

  return (
    <>
      <WhiteBoxShad
        shadColor={showDetails ? "main" : "shad"}
        onClick={hadleReviewClick}
        width="32%"
        margin="0.5rem 0"
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
            {!content.length || (
              <FlexDiv pointer={true}>
                <Count type="like" isChecked={liked} cnt={likeCnt} />
                <Selection
                  selectionList={
                    isMine ? selectionListMine : selectionListNotMine
                  }
                  forceClose={forceClose}
                />
              </FlexDiv>
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
      </WhiteBoxShad>

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
