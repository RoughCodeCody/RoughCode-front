import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const putCodeReviewFeedbackComplaint = (
  feedbackId: number
): Promise<null> => {
  return axios.put(`/code/rereview/${feedbackId}/complaint`);
};

type UsePutCodeReviewFeedbackComplaintOptions = {
  config?: MutationConfig<typeof putCodeReviewFeedbackComplaint>;
};

export const usePutCodeReviewFeedbackComplaint = ({
  config,
}: UsePutCodeReviewFeedbackComplaintOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: putCodeReviewFeedbackComplaint,
  });
};
