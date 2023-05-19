import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
const deleteEmailAuth = (): Promise<null> => {
  return axios.delete("/mypage/email");
};

export const useDeleteEmailAuth = () => {
  return useMutation({
    mutationFn: deleteEmailAuth,
  });
};
