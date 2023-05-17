import { create } from "zustand";

type ProjectFeedbackSelectionState = {
  selectedProjectFeedbackId: number[];
};

type ProjectFeedbackSelectionAction = {
  toggleProjectFeedbackSelection: (id: number) => void;
  resetProjectFeedbackSelection: () => void;
};

const initialState: ProjectFeedbackSelectionState = {
  selectedProjectFeedbackId: [],
};

export const useProjectFeedbackSelectionStore = create<
  ProjectFeedbackSelectionState & ProjectFeedbackSelectionAction
>((set) => ({
  ...initialState,
  toggleProjectFeedbackSelection: (id) => {
    set((state) => ({
      selectedProjectFeedbackId: state.selectedProjectFeedbackId.includes(id)
        ? state.selectedProjectFeedbackId.filter((el) => el !== id)
        : state.selectedProjectFeedbackId.concat([id]),
    }));
  },
  resetProjectFeedbackSelection: () => {
    set(initialState);
  },
}));
