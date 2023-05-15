import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

export const postCodeFav = (codeId: number): Promise<null> => {
  return axios.post(`/code/${codeId}/favorite`);
};

export const usePostCodeFav = () => {
  return useMutation({
    onSuccess: (_, codeId) =>
      queryClient.invalidateQueries(["codeInfo", codeId]),

    mutationFn: postCodeFav,
  });
};
