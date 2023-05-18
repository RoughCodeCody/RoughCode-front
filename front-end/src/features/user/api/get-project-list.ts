import { useInfiniteQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, InfiniteQueryConfig } from "@/lib/react-query";

// 코드에 프로젝트 연결할 때 내 프로젝트 목록 가져오기 위한 쿼리
interface MyProjectListResult {
  nextPage: number | undefined; // 다음 페이지
  list: {
    projectId: number;
    version: number;
    title: string;
    date?: Date;
    likeCnt: number;
    feedbackCnt: number;
    img: string;
    tags: string[];
    introduction: string;
    closed: boolean;
  }[];
}

type MyProjectListParams = {
  size: number;
};

type MyProjectListDTO = {
  page: number;
} & MyProjectListParams;

export const getMyProjectList = (
  params: MyProjectListDTO
): Promise<MyProjectListResult> => {
  return axios.get("/mypage/project", { params });
};

type QueryFnType = typeof getMyProjectList;

type UseProjectListOptions = {
  params: MyProjectListParams;
  config?: InfiniteQueryConfig<QueryFnType>;
};

export const useMyProjectList = ({ params, config }: UseProjectListOptions) => {
  return useInfiniteQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["myProjectList", params],
    queryFn: ({ pageParam = 0 }) =>
      getMyProjectList({ ...params, page: pageParam }),
  });
};
