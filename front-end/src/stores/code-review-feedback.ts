import { create } from "zustand";

type CodeReviewFeedbackData = {
  selectedLines: number[][];
  modifiedCode: string;
};

type CodeReviewFeedbackDataStore = {
  CodeReviewFeedbackData: CodeReviewFeedbackData;
  setSelectedLines: (selectedLines: number[][]) => void;
  setModifiedCode: (modifiedCode: string) => void;
};

export const useCodeReviewFeedbackDataStore =
  create<CodeReviewFeedbackDataStore>((set) => ({
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
  }));
