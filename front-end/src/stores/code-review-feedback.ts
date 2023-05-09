import { create } from "zustand";

type CodeReviewFeedbackData = {
  selectedLines: number[][];
  modifiedCode: string;
  feedbackContent: string;
};

type CodeReviewFeedbackDataStore = {
  CodeReviewFeedbackData: CodeReviewFeedbackData;
  setSelectedLines: (selectedLines: number[][]) => void;
  setModifiedCode: (modifiedCode: string) => void;
  setFeedbackContent: (FeedbackContent: string) => void;
  reset: () => void;
};

const initialState: CodeReviewFeedbackData = {
  selectedLines: [],
  modifiedCode: "",
  feedbackContent: "",
};

export const useCodeReviewFeedbackDataStore = create<
  CodeReviewFeedbackData & CodeReviewFeedbackDataStore
>((set) => ({
  ...initialState,
  CodeReviewFeedbackData: {
    selectedLines: [],
    modifiedCode: "",
    feedbackContent: "",
  },
  setSelectedLines: (selectedLines) => {
    set((state) => ({
      CodeReviewFeedbackData: {
        ...state.CodeReviewFeedbackData,
        selectedLines: selectedLines,
      },
    }));
  },
  setModifiedCode: (modifiedCode) => {
    set((state) => ({
      CodeReviewFeedbackData: {
        ...state.CodeReviewFeedbackData,
        modifiedCode: modifiedCode,
      },
    }));
  },
  setFeedbackContent: (FeedbackContent) => {
    set((state) => ({
      CodeReviewFeedbackData: {
        ...state.CodeReviewFeedbackData,
        FeedbackContent: FeedbackContent,
      },
    }));
  },
  reset: () => {
    set(initialState);
  },
}));
