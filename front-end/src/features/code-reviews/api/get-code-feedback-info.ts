import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType } from "@/lib/react-query";

import { CodeFeedbackInfoResult } from "../types";

export const getCodeReviewInfo = (
  feedbackId: number
): Promise<CodeFeedbackInfoResult> => {
  return axios.get(`/code/review/${feedbackId}`);
};

type QueryFnType = typeof getCodeReviewInfo;

export const useCodeFeedbackInfo = (feedbackId: number) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["codeFeedbackInfo", feedbackId],
    queryFn: () => getCodeReviewInfo(feedbackId),
  });
};
