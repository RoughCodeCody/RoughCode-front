import { useInfiniteQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, InfiniteQueryConfig } from "@/lib/react-query";

interface UserCodeListResult {
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

type UserCodeListParams = {
  endPoint: string;
  params: {
    size: number;
    page: number;
  };
};

export const getUserCodeList = ({
  endPoint,
  params,
}: UserCodeListParams): Promise<UserCodeListResult> => {
  if (endPoint === "feedback") {
    return axios.get("/mypage/code/review", { params });
  }
  return axios.get(`/mypage/code/${endPoint}`, { params });
};

type QueryFnType = typeof getUserCodeList;

type UseUserCodeListOptions = {
  endPoint: "" | "feedback" | "favorite";
  config?: InfiniteQueryConfig<QueryFnType>;
};

export const useUserCodeList = ({
  endPoint,
  config,
}: UseUserCodeListOptions) => {
  return useInfiniteQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["userCodeList", endPoint],
    queryFn: ({ pageParam = 0 }) => {
      const params = { size: 6, page: pageParam };
      return getUserCodeList({ endPoint, params });
    },
  });
};
