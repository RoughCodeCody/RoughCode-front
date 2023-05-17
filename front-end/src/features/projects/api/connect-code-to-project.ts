import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient, MutationConfig } from "@/lib/react-query";

export type ConnectCodeToProjectDTO = {
  projectId: number;
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
    onSuccess: (_, { projectId }) =>
      Promise.all([
        queryClient.invalidateQueries(["myCodeList"]),
        queryClient.invalidateQueries(["projectInfo", projectId]),
      ]),
    mutationFn: connectCodeToProject,
  });
};
