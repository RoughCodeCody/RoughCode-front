import { create } from "zustand";

type isCompleted = {
  editor: boolean;
  diffEditor: boolean;
};

type CodeReviewFeedbackData = {
  selectedLines: number[][];
  modifiedCode: string;
  feedbackContent: string;
  isCompleted: isCompleted;
};

type CodeReviewFeedbackDataStore = {
  CodeReviewFeedbackData: CodeReviewFeedbackData;
  setSelectedLines: (selectedLines: number[][]) => void;
  setModifiedCode: (modifiedCode: string) => void;
  setFeedbackContent: (FeedbackContent: string) => void;
  setIsCompleted: (whichEditor: keyof isCompleted) => void;
  reset: () => void;
};

const initialState: CodeReviewFeedbackData = {
  selectedLines: [],
  modifiedCode: "",
  feedbackContent: "",
  isCompleted: { editor: false, diffEditor: false },
};

export const useCodeReviewFeedbackDataStore = create<
  CodeReviewFeedbackData & CodeReviewFeedbackDataStore
>((set) => ({
  ...initialState,
  CodeReviewFeedbackData: {
    selectedLines: [],
    modifiedCode: "",
    feedbackContent: "",
    isCompleted: { editor: false, diffEditor: false },
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

  setIsCompleted: (whichEditor) => {
    set((state) => ({
      CodeReviewFeedbackData: {
        ...state.CodeReviewFeedbackData,
        isCompleted: {
          ...state.CodeReviewFeedbackData.isCompleted,
          [whichEditor]: !state.CodeReviewFeedbackData.isCompleted[whichEditor],
        },
      },
    }));
  },
}));
