import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

export const deleteCode = (codeId: number): Promise<null> => {
  return axios.delete(`/code/${codeId}`);
};

export const useDeleteCode = () => {
  return useMutation({
    mutationFn: deleteCode,
  });
};
