import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig, queryClient } from "@/lib/react-query";

import { ProjectUpdateValues } from "../types";

export type PutProjectDTO = {
  data: ProjectUpdateValues;
};

export const PutProject = ({
  data,
}: PutProjectDTO): Promise<{ projectId: number }> => {
  return axios.put(`/project/content`, data);
};

type usePutProjectOptions = {
  config?: MutationConfig<typeof PutProject>;
};

export const usePutProject = ({ config }: usePutProjectOptions = {}) => {
  return useMutation({
    // onMutate
    // onError
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["projects"] });
    },
    ...config,
    mutationFn: PutProject,
  });
};
