import { useInfiniteQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, InfiniteQueryConfig } from "@/lib/react-query";

interface UserProjectListResult {
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

type UserProjectListParams = {
  endPoint: string;
  params: {
    size: number;
    page: number;
  };
};

export const getUserProjectList = ({
  endPoint,
  params,
}: UserProjectListParams): Promise<UserProjectListResult> => {
  return axios.get(`/mypage/project/${endPoint}`, { params });
};

type QueryFnType = typeof getUserProjectList;

type UseUserProjectListOptions = {
  endPoint: "" | "feedback" | "favorite";
  config?: InfiniteQueryConfig<QueryFnType>;
};

export const useUserProjectList = ({
  endPoint,
  config,
}: UseUserProjectListOptions) => {
  return useInfiniteQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["userProjectList"],
    queryFn: ({ pageParam = 0 }) => {
      const params = { size: 3, page: pageParam };
      return getUserProjectList({ endPoint, params });
    },
  });
};
