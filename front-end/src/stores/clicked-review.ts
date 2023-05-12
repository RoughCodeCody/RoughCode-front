import { create } from "zustand";

import { Review } from "@/features/code-reviews";

interface ClickedReviewStore {
  clickedReview: Review;
  setClickedReviewId: (id: number) => void;
}

export const useClickedReviewStore = create<ClickedReviewStore>((set) => ({
  clickedReview: {
    reviewId: -1,
    userId: -1,
    userName: "",
    codeContent: "",
    content: "",
    language: "",
    lineNumbers: [],
    likeCnt: 0,
    selected: 0,
    liked: false,
    date: new Date(),
    reReviews: [],
  },
  setClickedReviewId: (id: number) =>
    set((state) => ({
      clickedReview: {
        ...state.clickedReview,
        reviewId: id,
      },
    })),
}));
