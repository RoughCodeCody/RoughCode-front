import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const putProjectFeedbackComplaint = (
  feedbackId: number
): Promise<null> => {
  return axios.put(`/project/feedback/${feedbackId}/complaint`);
};

type UsePutProjectFeedbackComplaintOptions = {
  config?: MutationConfig<typeof putProjectFeedbackComplaint>;
};

export const usePutProjectFeedbackComplaint = ({
  config,
}: UsePutProjectFeedbackComplaintOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: putProjectFeedbackComplaint,
  });
};
