import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { ProjectFeedbackInfo } from "../types";

export const getProjectFeedbacks = ({
  projectId,
}: {
  projectId: string;
}): Promise<ProjectFeedbackInfo[]> => {
  return axios.get(`/project/${projectId}/feedback`);
};

type QueryFnType = typeof getProjectFeedbacks;

type UseProjectFeedbacksOptions = {
  projectId: string;
  config?: QueryConfig<QueryFnType>;
};

export const useProjectFeedbacks = ({
  projectId,
  config,
}: UseProjectFeedbacksOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["feedbacks", projectId],
    queryFn: () => getProjectFeedbacks({ projectId }),
    ...config,
  });
};
