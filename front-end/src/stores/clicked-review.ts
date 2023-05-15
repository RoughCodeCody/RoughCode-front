import { create } from "zustand";

interface codeReviewFeedback {
  reReviewId: number; // 코드 리리뷰 아이디
  userId: number; // 코드 리리뷰 작성자 id (0이면 익명)
  userName: string; // 리리뷰 남긴 사람 닉네임(빈 문자열이면 익명)
  likeCnt: number; // 코드 리리뷰 좋아요 수
  liked: boolean; // 좋아요 누른 여부
  content: string; // 코드 리리뷰 내용
  createdDate: Date; // 코드 리리뷰 작성 날짜
  modifiedDate: Date; // 코드 리리뷰 수정 날짜
}

interface ReviewInfo {
  reviewId: number;
  reReviews: codeReviewFeedback[];
  lineNumbers: number[][];
  codeContent: string;
  content: string;
}

interface ClickedReviewStore {
  clickedReview: ReviewInfo;
  setClickedReviewInfo: (clickedReview: ReviewInfo) => void;
}

export const useClickedReviewStore = create<ClickedReviewStore>((set) => ({
  clickedReview: {
    reviewId: -1,
    codeContent: "",
    content: "등록된 코드 리뷰가 없어요",
    lineNumbers: [],
    reReviews: [],
  },
  setClickedReviewInfo: (clickedReview) => set(() => ({ clickedReview })),
}));
