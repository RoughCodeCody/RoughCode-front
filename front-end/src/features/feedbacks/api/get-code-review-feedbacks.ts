import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { CodeReviewInfoResult } from "@/features/code-reviews";

// 코드 상세 페이지에서 리뷰에 대한 정보 및 피드백을 가져오기 위한 api
export const getCodeReviewFeedbacks = (
  reviewId: number
): Promise<CodeReviewInfoResult> => {
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
