import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { CodeReviewInfoResult } from "@/features/code-reviews";

// 코드 상세 페이지에서 리뷰에 대한 정보 및 피드백을 가져오기 위한 api
export const getCodeReviewFeedbacks = (
  clickedReviewId: number
): Promise<CodeReviewInfoResult> => {
  return axios.get(`/code/review/${clickedReviewId}`);
};

type QueryFnType = typeof getCodeReviewFeedbacks;

export const useCodeReviewFeedbacks = (clickedReviewId: number) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["codeReviewFeedbacks", clickedReviewId],
    queryFn: () => getCodeReviewFeedbacks(clickedReviewId),
  });
};
