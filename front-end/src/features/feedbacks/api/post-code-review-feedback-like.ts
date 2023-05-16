import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

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
