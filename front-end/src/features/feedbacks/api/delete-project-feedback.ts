import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const deleteProjectFeedback = (feedbackId: number): Promise<null> => {
  return axios.delete(`/project/feedback/${feedbackId}`);
};

type UseDeleteProjectFeedbackOptions = {
  config?: MutationConfig<typeof deleteProjectFeedback>;
};

export const useDeleteProjectFeedback = ({
  config,
}: UseDeleteProjectFeedbackOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: deleteProjectFeedback,
  });
};
