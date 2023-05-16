import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export type PostCodeReviewFeedbackDTO = {
  id: number;
  content: string;
};

export const postCodeReviewFeedback = (
  data: PostCodeReviewFeedbackDTO
): Promise<null> => {
  return axios.post(`/code/rereview`, data);
};

type usePostCodeReviewFeedbackOptions = {
  config?: MutationConfig<typeof postCodeReviewFeedback>;
};

export const usePostCodeReviewFeedback = ({
  config,
}: usePostCodeReviewFeedbackOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: postCodeReviewFeedback,
  });
};
