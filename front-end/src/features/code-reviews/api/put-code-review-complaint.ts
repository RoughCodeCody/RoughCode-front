import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const putCodeReviewComplaint = (reviewId: number): Promise<null> => {
  return axios.put(`/code/review/${reviewId}/complaint`);
};

type UsePutCodeReviewComplaintOptions = {
  config?: MutationConfig<typeof putCodeReviewComplaint>;
};

export const usePutCodeReviewComplaint = ({
  config,
}: UsePutCodeReviewComplaintOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: putCodeReviewComplaint,
  });
};
