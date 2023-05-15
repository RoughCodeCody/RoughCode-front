import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
const deleteEmailAuth = (): Promise<null> => {
  console.log("지워라!");
  return axios.delete("/mypage/email");
};

export const useDeleteEmailAuth = () => {
  return useMutation({
    mutationFn: deleteEmailAuth,
  });
};
