import type { MutationConfig } from "@/lib/react-query";
import type { ProjectUpdateValues } from "../types";

import { useMutation } from "@tanstack/react-query";
import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

type PutProjectDTO = {
  data: ProjectUpdateValues;
};

export const putProject = ({ data }: PutProjectDTO): Promise<number> => {
  return axios.put(`/project/content`, data);
};

type UsePutProjectOptions = {
  config?: MutationConfig<typeof putProject>;
};

export const usePutProject = ({ config }: UsePutProjectOptions = {}) => {
  return useMutation({
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["projects"] });
    },
    ...config,
    mutationFn: putProject,
  });
};
