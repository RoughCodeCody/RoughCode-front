import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";
import { useMutation } from "@tanstack/react-query";

export const postCodeReviewFeedbackLike = (
  feedbackId: number
): Promise<null> => {
  return axios.post(`/code/rereview/${feedbackId}/like`);
};

export const usePostCodeReviewFeedbackLike = () => {
  return useMutation({
    mutationFn: postCodeReviewFeedbackLike,
  });
};
