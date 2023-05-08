import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

export type PutProjectOpenStatusDTO = {
  projectId: string;
  status: string;
};

export const putProjectOpenStatus = ({
  projectId,
  status,
}: PutProjectOpenStatusDTO): Promise<null> => {
  return axios.put(`/project/${projectId}/${status}`);
};

export const usePutProjectOpenStatus = () => {
  return useMutation({
    onSuccess: (_, newOpenStatus) =>
      queryClient.invalidateQueries({
        queryKey: ["projectInfo", newOpenStatus.projectId],
      }),

    mutationFn: putProjectOpenStatus,
  });
};
