import { useInfiniteQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, InfiniteQueryConfig } from "@/lib/react-query";

interface ProjectListResult {
  nextPage: number | undefined; // 다음 페이지
  list: {
    projectId: number;
    version: number;
    title: string;
    date: Date;
    likeCnt: number;
    feedbackCnt: number;
    img: string;
    tags: string[];
    introduction: string;
    closed: boolean;
  }[];
}

type SortOption = "modifiedDate" | "likeCnt" | "feedbackCnt" | "reviewCnt";

type ProjectListParams = {
  sort: SortOption;
  size: number;
  keyword?: string;
  tagIdList?: string;
  closed: 0 | 1;
};

type ProjectListDTO = {
  page: number;
} & ProjectListParams;

export const getProjectList = (
  params: ProjectListDTO
): Promise<ProjectListResult> => {
  return axios.get("/project", { params });
};

type QueryFnType = typeof getProjectList;

type UseProjectListOptions = {
  params: ProjectListParams;
  config?: InfiniteQueryConfig<QueryFnType>;
};

export const useProjectList = ({ params, config }: UseProjectListOptions) => {
  return useInfiniteQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["projectList", params],
    queryFn: ({ pageParam = 0 }) =>
      getProjectList({ ...params, page: pageParam }),
  });
};
