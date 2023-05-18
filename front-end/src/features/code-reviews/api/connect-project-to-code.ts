import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient, MutationConfig } from "@/lib/react-query";

export type ConnectProjectToCodeDTO = {
  codeId: number;
  data: { projectId: number };
};

export const connectProjectToCode = ({
  codeId,
  data,
}: ConnectProjectToCodeDTO): Promise<null> => {
  return axios.put(`/code/${codeId}/connect`, data);
};

export const useConnectProjectToCode = () => {
  return useMutation({
    onSuccess: (_, { codeId }) =>
      Promise.all([
        queryClient.invalidateQueries(["myProjectList"]),
        queryClient.invalidateQueries(["codeInfo", codeId]),
      ]),
    mutationFn: connectProjectToCode,
  });
};
