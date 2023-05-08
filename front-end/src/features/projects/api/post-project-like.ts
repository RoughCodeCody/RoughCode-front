import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

export const postProjectLike = (projectId: string): Promise<null> => {
  return axios.post(`/project/${projectId}/like`);
};

export const usePostProjectLike = () => {
  return useMutation({
    onSuccess: (_, projectId) =>
      queryClient.invalidateQueries({
        queryKey: ["projectInfo", projectId],
      }),

    mutationFn: postProjectLike,
  });
};
