import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { ProjectInfoResult } from "../types";

export const getProjectInfo = (
  projectId: string
): Promise<ProjectInfoResult> => {
  return axios.get(`/project/${projectId}`);
};

type QueryFnType = typeof getProjectInfo;

type UseProjectInfoOptions = {
  projectId: string;
  config?: QueryConfig<QueryFnType>;
};

export const useProjectInfo = ({
  projectId,
  config,
}: UseProjectInfoOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["projectInfo", projectId],
    queryFn: () => getProjectInfo(projectId),
  });
};
