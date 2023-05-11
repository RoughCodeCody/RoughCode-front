import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const deleteProject = (projectId: string): Promise<null> => {
  return axios.delete(`/project/${projectId}`);
};

type UseDeleteProjectOptions = {
  config?: MutationConfig<typeof deleteProject>;
};

export const useDeleteProject = ({ config }: UseDeleteProjectOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: deleteProject,
  });
};
