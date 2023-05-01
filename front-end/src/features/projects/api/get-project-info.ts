import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

type ProjectInfoParams = {
  projectId: string;
};

export const getProjectInfo = ({
  projectId,
}: ProjectInfoParams): Promise<ProjectInfoParams> => {
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
    queryFn: () => getProjectInfo({ projectId }),
  });
};
