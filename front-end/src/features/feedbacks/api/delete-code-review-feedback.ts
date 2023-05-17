import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const deleteCodeReviewFeedback = (feedbackId: number): Promise<null> => {
  return axios.delete(`/code/rereview/${feedbackId}`);
};

type UseDeleteCodeReviewFeedbackOptions = {
  config?: MutationConfig<typeof deleteCodeReviewFeedback>;
};

export const useDeleteCodeReviewFeedback = ({
  config,
}: UseDeleteCodeReviewFeedbackOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: deleteCodeReviewFeedback,
  });
};
