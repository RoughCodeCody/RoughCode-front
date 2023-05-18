import create from "zustand";
import { mountStoreDevtool } from "simple-zustand-devtools";

type CodeReviewForCodeUpdateSelectionState = {
  selectedCodeReviewId: number[];
};

type CodeReviewForCodeUpdateSelectionAction = {
  toggleCodeReviewForCodeUpdateSelection: (id: number) => void;
  resetCodeReviewForCodeUpdateSelection: () => void;
};

const initialState: CodeReviewForCodeUpdateSelectionState = {
  selectedCodeReviewId: [],
};

export const useCodeReviewsForCodeUpdateSelection = create<
  CodeReviewForCodeUpdateSelectionState & CodeReviewForCodeUpdateSelectionAction
>((set) => ({
  ...initialState,
  toggleCodeReviewForCodeUpdateSelection: (id: number) => {
    set((state) => ({
      selectedCodeReviewId: state.selectedCodeReviewId.includes(id)
        ? state.selectedCodeReviewId.filter((el) => el !== id)
        : state.selectedCodeReviewId.concat([id]),
    }));
  },
  resetCodeReviewForCodeUpdateSelection: () => {
    set(initialState);
  },
}));

if (process.env.NODE_ENV === "development") {
  mountStoreDevtool(
    "CodeReviewsForCodeUpdateSelection",
    useCodeReviewsForCodeUpdateSelection
  );
}
