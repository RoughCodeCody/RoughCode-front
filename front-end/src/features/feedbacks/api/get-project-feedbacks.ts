import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { ProjectFeedbackInfo } from "../types";

export const getProjectFeedbacks = ({
  projectId,
  versionUp,
}: {
  projectId: number;
  versionUp: string;
}): Promise<ProjectFeedbackInfo[]> => {
  return axios.get(`/project/${projectId}/feedback`, { params: { versionUp } });
};

type QueryFnType = typeof getProjectFeedbacks;

type UseProjectFeedbacksOptions = {
  projectId: number;
  versionUp: string;
  config?: QueryConfig<QueryFnType>;
};

export const useProjectFeedbacks = ({
  projectId,
  versionUp,
  config,
}: UseProjectFeedbacksOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["feedbacks", projectId, versionUp],
    queryFn: () => getProjectFeedbacks({ projectId, versionUp }),
    ...config,
  });
};
