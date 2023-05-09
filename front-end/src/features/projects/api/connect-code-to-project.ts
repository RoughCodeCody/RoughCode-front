import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

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

export const useConnectCodeToProject = () => {
  return useMutation({
    onSuccess: () =>
      queryClient.invalidateQueries({
        queryKey: ["myCodeList"],
      }),

    mutationFn: connectCodeToProject,
  });
};
