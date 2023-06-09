import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig, queryClient } from "@/lib/react-query";

import { ProjectUpdateValues } from "../types";

export type PostProjectDTO = {
  data: ProjectUpdateValues;
};

export const postProject = ({
  data,
}: PostProjectDTO): Promise<{ projectId: number }> => {
  return axios.post(`/project/content`, data);
};

type usePostProjectOptions = {
  config?: MutationConfig<typeof postProject>;
};

export const usePostProject = ({ config }: usePostProjectOptions = {}) => {
  return useMutation({
    // onMutate
    // onError
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["projects"] });
    },
    ...config,
    mutationFn: postProject,
  });
};
