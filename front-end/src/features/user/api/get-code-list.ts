import { useInfiniteQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, InfiniteQueryConfig } from "@/lib/react-query";

// 프로젝트에 코드 연결할 때 내 코드 목록 가져오기 위한 쿼리
interface MyCodeListResult {
  nextPage: number | undefined; // 다음 페이지
  list: {
    codeId: number; // 코드 ID
    version: number; // 코드 버전
    title: string; // 코드 제목
    date: Date; // 수정날짜
    likeCnt: number; // 좋아요 수
    reviewCnt: number; // 리뷰 수
    tags: { tagId: number; name: string; cnt: number }[]; // 태그 이름 목록
    userName: string; // 코드 작성자 닉네임
    liked: boolean; // 좋아요 여부
  }[];
}

type MyCodeListParams = {
  size: number;
};

type MyCodeListDTO = {
  page: number;
} & MyCodeListParams;

export const getMyCodeList = (
  params: MyCodeListDTO
): Promise<MyCodeListResult> => {
  return axios.get("/mypage/code", { params });
};

type QueryFnType = typeof getMyCodeList;

type UseCodeListOptions = {
  params: MyCodeListParams;
  config?: InfiniteQueryConfig<QueryFnType>;
};

export const useMyCodeList = ({ params, config }: UseCodeListOptions) => {
  return useInfiniteQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["myCodeList", params],
    queryFn: ({ pageParam = 0 }) =>
      getMyCodeList({ ...params, page: pageParam }),
  });
};
