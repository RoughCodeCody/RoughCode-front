import type { MutationConfig } from "@/lib/react-query";
import type { ProjectUpdateValues } from "../types";

import { useMutation } from "@tanstack/react-query";
import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

type PostProjectDTO = {
  data: ProjectUpdateValues;
};

export const postProject = ({ data }: PostProjectDTO): Promise<number> => {
  return axios.post(`/project/content`, data);
};

type UsePostProjectOptions = {
  config?: MutationConfig<typeof postProject>;
};

export const usePostProject = ({ config }: UsePostProjectOptions = {}) => {
  return useMutation({
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["projects"] });
    },
    ...config,
    mutationFn: postProject,
  });
};
