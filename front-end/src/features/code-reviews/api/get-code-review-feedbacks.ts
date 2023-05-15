import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { CodeFeedbackInfoResult } from "../types";

export const getCodeReviewFeedbacks = (
  reviewId: number
): Promise<CodeFeedbackInfoResult> => {
  return axios.get(`/code/review/${reviewId}`);
};

type QueryFnType = typeof getCodeReviewFeedbacks;

type UseCodeReviewFeedbacksOptions = {
  reviewId: number;
  config?: QueryConfig<QueryFnType>;
};

export const useCodeReviewFeedbacks = ({
  reviewId,
  config,
}: UseCodeReviewFeedbacksOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["codeReviewFeedbacks", reviewId],
    queryFn: () => getCodeReviewFeedbacks(reviewId),
  });
};
