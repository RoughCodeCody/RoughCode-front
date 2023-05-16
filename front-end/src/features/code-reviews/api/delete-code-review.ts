import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

export const deleteCodeReview = (reviewId: number): Promise<null> => {
  return axios.delete(`/code/review/${reviewId}`);
};

export const useDeleteCodeReview = () => {
  return useMutation({
    mutationFn: deleteCodeReview,
  });
};
