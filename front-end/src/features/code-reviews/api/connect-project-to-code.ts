import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

export type ConnectProjectToCodeDTO = {
  codeId: number;
  projectId: number;
};

export const connectProjectToCode = ({
  codeId,
  projectId,
}: ConnectProjectToCodeDTO): Promise<null> => {
  return axios.put(`/code/${codeId}/connect/${projectId}`);
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
