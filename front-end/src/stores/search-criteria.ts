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
  setSort: (sortOption: string) => void;
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
  setSort: (sortOption) => {
    let mappedSortOption: "modifiedDate" | "likeCnt" | "feedbackCnt";
    if (sortOption === "최신순") {
      mappedSortOption = "modifiedDate";
    } else if (sortOption === "좋아요순") {
      mappedSortOption = "likeCnt";
    } else if (sortOption === "리뷰순") {
      mappedSortOption = "feedbackCnt";
    }
    set((state) => ({
      searchCriteria: {
        ...state.searchCriteria,
        sort: mappedSortOption,
      },
    }));
  },
}));
