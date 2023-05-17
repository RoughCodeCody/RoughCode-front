import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";
import { useMutation } from "@tanstack/react-query";

export const postCodeReviewLike = (reviewId: number): Promise<null> => {
  return axios.post(`/code/review/${reviewId}/like`);
};

type usePostCodeReviewLikeOptions = {
  config?: MutationConfig<typeof postCodeReviewLike>;
};

export const usePostCodeReviewLike = ({
  config,
}: usePostCodeReviewLikeOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: postCodeReviewLike,
  });
};
