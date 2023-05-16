import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";
import { useMutation } from "@tanstack/react-query";

export const postCodeReviewLike = (reviewId: number): Promise<null> => {
  return axios.post(`/code/review/${reviewId}/like`);
};

export const usePostCodeReviewLike = () => {
  return useMutation({
    onSuccess: (_, reviewId) =>
      queryClient.invalidateQueries(["codeReviewFeedbacks", reviewId]),

    mutationFn: postCodeReviewLike,
  });
};
