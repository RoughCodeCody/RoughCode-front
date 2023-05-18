import { create } from "zustand";

type Language = {
  tagId: number;
  name: string;
};

type SelectedLanguageStore = {
  selectedLanguage: Language[];
  setSelectedLanguage: (newLanguage: Language[]) => void;
  reset: () => void;
};

export const useSelectedLanguageStore = create<SelectedLanguageStore>(
  (set) => ({
    selectedLanguage: [],
    setSelectedLanguage: (newLanguage) => {
      set(() => ({ selectedLanguage: newLanguage }));
    },
    reset: () => {
      set(() => ({ selectedLanguage: [] }));
    },
  })
);
