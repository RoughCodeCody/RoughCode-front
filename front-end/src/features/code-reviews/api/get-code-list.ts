import { useInfiniteQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, InfiniteQueryConfig } from "@/lib/react-query";

interface CodeListResult {
  nextPage: number | undefined; // 다음 페이지
  list: {
    codeId: number; // 코드 ID
    version: number; // 코드 버전
    title: string; // 코드 제목
    date: Date; // 수정날짜
    likeCnt: number; // 좋아요 수
    reviewCnt: number; // 리뷰 수
    tags: string[]; // 태그 이름 목록
    userName: string; // 코드 작성자 닉네임
    liked: boolean; // 좋아요 여부
  }[];
}

type SortOption = "modifiedDate" | "likeCnt" | "feedbackCnt" | "reviewCnt";

type CodeListParams = {
  sort: SortOption;
  size: number;
  keyword: string;
  tagIdList: string;
};

type CodeListDTO = {
  page: number;
} & CodeListParams;

export const getCodeList = (params: CodeListDTO): Promise<CodeListResult> => {
  return axios.get("/code", { params });
};

type QueryFnType = typeof getCodeList;

type UseCodeListOptions = {
  params: CodeListParams;
  config?: InfiniteQueryConfig<QueryFnType>;
};

export const useCodeList = ({ params, config }: UseCodeListOptions) => {
  return useInfiniteQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["codeList", params],
    queryFn: ({ pageParam = 0 }) => getCodeList({ ...params, page: pageParam }),
  });
};
