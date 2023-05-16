import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export type PutCodeReviewFeedbackDTO = {
  feedbackId: number;
  content: string;
};

export const putCodeReviewFeedback = (
  data: PutCodeReviewFeedbackDTO
): Promise<null> => {
  return axios.put(`/code/rereview`, data);
};

type UsePutCodeReviewFeedbackOptions = {
  config?: MutationConfig<typeof putCodeReviewFeedback>;
};

export const usePutCodeReviewFeedback = ({
  config,
}: UsePutCodeReviewFeedbackOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: putCodeReviewFeedback,
  });
};
