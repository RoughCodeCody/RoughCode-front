import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export type PutProjectFeedbackDTO = {
  feedbackId: number;
  content: string;
};

export const putProjectFeedback = (
  data: PutProjectFeedbackDTO
): Promise<null> => {
  return axios.put(`/project/feedback`, data);
};

type UsePutProjectFeedbackOptions = {
  config?: MutationConfig<typeof putProjectFeedback>;
};

export const usePutProjectFeedback = ({
  config,
}: UsePutProjectFeedbackOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: putProjectFeedback,
  });
};
