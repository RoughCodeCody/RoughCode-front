import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient, MutationConfig } from "@/lib/react-query";

export type ConnectCodeToProjectDTO = {
  projectId: string;
  data: number[];
};

export const connectCodeToProject = ({
  projectId,
  data,
}: ConnectCodeToProjectDTO): Promise<null> => {
  return axios.put(`/project/${projectId}/connect`, data);
};

type useConnectCodeToProjectOptions = {
  config?: MutationConfig<typeof connectCodeToProject>;
};

export const useConnectCodeToProject = ({
  config,
}: useConnectCodeToProjectOptions = {}) => {
  return useMutation({
    ...config,
    onSuccess: () =>
      queryClient.invalidateQueries({
        queryKey: ["myCodeList"],
      }),

    mutationFn: connectCodeToProject,
  });
};
