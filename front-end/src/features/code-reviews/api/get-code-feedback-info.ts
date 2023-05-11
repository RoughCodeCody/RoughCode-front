import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType } from "@/lib/react-query";

import { CodeFeedbackInfoResult } from "../types";

export const getCodeFeedbackInfo = (
  feedbackId: number
): Promise<CodeFeedbackInfoResult> => {
  return axios.get(`/code/review/${feedbackId}`);
};

type QueryFnType = typeof getCodeFeedbackInfo;

export const useCodeFeedbackInfo = (feedbackId: number) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["codeFeedbackInfo", feedbackId],
    queryFn: () => getCodeFeedbackInfo(feedbackId),
  });
};
