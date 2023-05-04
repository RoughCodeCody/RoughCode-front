import { create } from "zustand";

type Tag = {
  tagId: number;
  name: string;
};

type SortOption = "modifiedDate" | "likeCnt" | "feedbackCnt" | "reviewCnt";

type SearchCriteria = {
  keyword: string;
  tagIdList: Tag[];
  closed: 0 | 1;
  sort: SortOption;
  page: number;
  size: number;
};

type SearchCriteriaStore = {
  searchCriteria: SearchCriteria;
  setClosedValue: (closed: 0 | 1) => void;
  setSort: (sortOption: string, type: "project" | "codeReview") => void;
  addTagId: (Tag: Tag) => void;
  deleteTagId: (TagId: number) => void;
  setKeyword: (keyword: string) => void;
};

export const useSearchCriteriaStore = create<SearchCriteriaStore>((set) => ({
  searchCriteria: {
    sort: "modifiedDate",
    page: 0,
    size: 9,
    keyword: "",
    tagIdList: [],
    closed: 1,
  },
  setClosedValue: (ClosedValue) => {
    // 전체 프로젝트 보기, 열린 프로젝트만 보기
    set((state) => ({
      searchCriteria: {
        ...state.searchCriteria,
        keyword: "",
        closed: ClosedValue,
      },
    }));
  },
  setSort: (sortOption, type) => {
    // 최신순, 좋아요순, 리뷰순 정렬
    let mappedSortOption: SortOption;

    if (sortOption === "최신순") {
      mappedSortOption = "modifiedDate";
    } else if (sortOption === "좋아요순") {
      mappedSortOption = "likeCnt";
    } else if (sortOption === "리뷰순") {
      mappedSortOption = type === "project" ? "feedbackCnt" : "reviewCnt";
    }
    set((state) => ({
      searchCriteria: {
        ...state.searchCriteria,
        sort: mappedSortOption,
      },
    }));
  },
  addTagId: (Tag) => {
    // 이미 있는 태그는 추가하지 않음
    set((state) => {
      if (state.searchCriteria.tagIdList.find((el) => el.tagId === Tag.tagId))
        return state;
      else
        return {
          searchCriteria: {
            ...state.searchCriteria,
            keyword: "",
            tagIdList: [...state.searchCriteria.tagIdList, Tag],
          },
        };
    });
  },
  deleteTagId: (TagId) => {
    // 선택된 태그에서 제거
    set((state) => {
      let newTagIdList: Tag[];
      newTagIdList = state.searchCriteria.tagIdList.filter(
        (item) => item.tagId != TagId
      );
      return {
        searchCriteria: {
          ...state.searchCriteria,
          keyword: "",
          tagIdList: newTagIdList,
        },
      };
    });
  },
  setKeyword: (keyword) => {
    // 전체 프로젝트 보기, 열린 프로젝트만 보기
    set((state) => ({
      searchCriteria: {
        ...state.searchCriteria,
        keyword: keyword,
      },
    }));
  },
}));
