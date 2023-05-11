import { create } from "zustand";

interface ClickedReviewStore {
  initialState: { clickedReviewId: number };
  setClickedReviewId: (id: number) => void;
}

export const useClickedReviewStore = create<ClickedReviewStore>((set) => ({
  initialState: { clickedReviewId: -1 },
  setClickedReviewId: (id: number) =>
    set(() => ({ initialState: { clickedReviewId: id } })),
}));
