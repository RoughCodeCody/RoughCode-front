import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const putFeedbackComplaint = (feedbackId: number): Promise<null> => {
  return axios.put(`/project/feedback/${feedbackId}/complaint`);
};

type UsePutFeedbackComplaintOptions = {
  config?: MutationConfig<typeof putFeedbackComplaint>;
};

export const usePutFeedbackComplaint = ({
  config,
}: UsePutFeedbackComplaintOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: putFeedbackComplaint,
  });
};
