import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

export const postCodeLike = (codeId: number): Promise<null> => {
  return axios.post(`/code/${codeId}/like`);
};

export const usePostCodeLike = () => {
  return useMutation({
    onSuccess: (_, codeId) =>
      queryClient.invalidateQueries({
        queryKey: ["codeInfo", codeId],
      }),

    mutationFn: postCodeLike,
  });
};
