import create from "zustand";

type SearchCriteria = {
  keyword: string;
  tagIdList: number[];
  closed: boolean;
  sort: "modifiedDate" | "likeCnt" | "feedbackCnt";
  page: number;
  size: number;
};

type SearchCriteriaStore = {
  searchCriteria: SearchCriteria;
  setClosedValue: (closed: boolean) => void;
};

export const useSearchCriteriaStore = create<SearchCriteriaStore>((set) => ({
  searchCriteria: {
    keyword: "",
    tagIdList: [],
    closed: true,
    sort: "modifiedDate",
    page: 0,
    size: 9,
  },
  setClosedValue: (ClosedValue) => {
    set((state) => ({
      searchCriteria: {
        ...state.searchCriteria,
        keyword: "",
        closed: ClosedValue,
      },
    }));
  },
}));
