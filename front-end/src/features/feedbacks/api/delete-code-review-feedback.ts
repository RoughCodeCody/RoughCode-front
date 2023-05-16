import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

export const deleteCodeReviewFeedback = (feedbackId: number): Promise<null> => {
  return axios.delete(`/code/rereview/${feedbackId}`);
};

export const useDeleteCodeReviewFeedback = () => {
  return useMutation({
    mutationFn: deleteCodeReviewFeedback,
  });
};
