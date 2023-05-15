import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType } from "@/lib/react-query";

import { CodeReviewInfoResult } from "../types";

export const getCodeReviewInfo = (
  codeReviewId: number
): Promise<CodeReviewInfoResult> => {
  return axios.get(`/code/review/${codeReviewId}`);
};

type QueryFnType = typeof getCodeReviewInfo;

export const useCodeReviewInfo = (codeReviewId: number) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["codeReviewInfo", codeReviewId],
    queryFn: () => getCodeReviewInfo(codeReviewId),
  });
};
