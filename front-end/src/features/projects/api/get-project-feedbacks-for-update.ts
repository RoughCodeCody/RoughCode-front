import type { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";
import type { ProjectFeedbackForUpdate } from "../types";

import { useQuery } from "@tanstack/react-query";
import { axios } from "@/lib/axios";

export const getProjectFeedbacksForUpdate = ({
  projectId,
}: {
  projectId: string;
}): Promise<ProjectFeedbackForUpdate[]> => {
  return axios.get(`/project/${projectId}/feedback`);
};

type QueryFnType = typeof getProjectFeedbacksForUpdate;

type UseProjectFeedbacksForUpdateOptions = {
  projectId: string;
  config?: QueryConfig<QueryFnType>;
};

export const useProjectFeedbacksForUpdate = ({
  projectId,
  config,
}: UseProjectFeedbacksForUpdateOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["projectFeedbacksForUpdate", projectId],
    queryFn: () => getProjectFeedbacksForUpdate({ projectId }),
    ...config,
  });
};
