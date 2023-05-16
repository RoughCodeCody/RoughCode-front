import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const postProjectFeedbackLike = (feedbackId: number): Promise<null> => {
  return axios.post(`/project/feedback/${feedbackId}/like`);
};

type UsePostProjectFeedbackLikeOptions = {
  config?: MutationConfig<typeof postProjectFeedbackLike>;
};

export const usePostProjectFeedbackLike = ({
  config,
}: UsePostProjectFeedbackLikeOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: postProjectFeedbackLike,
  });
};
