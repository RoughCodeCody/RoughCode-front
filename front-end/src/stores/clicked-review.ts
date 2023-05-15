import { create } from "zustand";
import { CodeReviewFeedback } from "@/features/code-reviews";

interface ReviewInfo {
  reviewId: number;
  reReviews: CodeReviewFeedback[];
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
