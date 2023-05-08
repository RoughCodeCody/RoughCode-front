import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

export const postProjectFav = (projectId: string): Promise<null> => {
  return axios.post(`/project/${projectId}/favorite`);
};

export const usePostProjectFav = () => {
  return useMutation({
    onSuccess: (_, projectId) =>
      queryClient.invalidateQueries({
        queryKey: ["projectInfo", projectId],
      }),

    mutationFn: postProjectFav,
  });
};
