import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const postFeedbackLike = (feedbackId: number): Promise<null> => {
  return axios.post(`/project/feedback/${feedbackId}/like`);
};

type UsePostFeedbackLikeOptions = {
  config?: MutationConfig<typeof postFeedbackLike>;
};

export const usePostFeedbackLike = ({
  config,
}: UsePostFeedbackLikeOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: postFeedbackLike,
  });
};
