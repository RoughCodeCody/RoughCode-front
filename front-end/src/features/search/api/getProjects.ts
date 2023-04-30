import { useQuery, useInfiniteQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { ProjectsList } from "../types";

import { useSearchCriteriaStore } from "@/stores";
// get 요청으로 바꿔야함, 오히려 쉬워짐
// 나머지 세개의 파람스만 추가하면 됨
// 그리고 getCodeList는 새로 파일 만들어서 하자

export const getProjects = ({
  sort,
  page,
  size,
  keyword,
  stringTagIdList,
  closed,
}: {
  sort: "modifiedDate" | "likeCnt" | "feedbackCnt";
  page: number;
  size: number;
  keyword?: String;
  stringTagIdList?: string;
  closed: 0 | 1;
}): Promise<ProjectsList[]> => {
  return axios.get(`/project`, {
    params: {
      sort: sort,
      page: page,
      size: size,
      keyword: keyword,
      tagIdList: stringTagIdList,
      closed: closed,
    },
  });
};

type QueryFnType = typeof getProjects;

type UseProjectsOptions = {
  sort: "modifiedDate" | "likeCnt" | "feedbackCnt";
  page: number;
  size: number;
  keyword?: String;
  tagIdList?: number[];
  closed: 0 | 1;
  config?: QueryConfig<QueryFnType>;
};

export const useProjects = ({
  sort,
  page,
  size,
  keyword,
  tagIdList,
  closed,
  config,
}: UseProjectsOptions) => {
  const stringTagIdList =
    tagIdList !== undefined && tagIdList.length !== 0
      ? tagIdList.join(",")
      : undefined;

  return useInfiniteQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["projects", sort, page, size, keyword, tagIdList, closed],
    queryFn: () =>
      getProjects({ sort, page, size, keyword, stringTagIdList, closed }),
    // ...config,
  });
};
