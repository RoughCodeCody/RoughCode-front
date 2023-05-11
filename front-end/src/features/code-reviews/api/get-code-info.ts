import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType } from "@/lib/react-query";

import { CodeInfoResult } from "../types";

export const getCodeInfo = (codeId: number): Promise<CodeInfoResult> => {
  return axios.get(`/code/${codeId}`);
};

type QueryFnType = typeof getCodeInfo;

export const useCodeInfo = (codeId: number) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["codeInfo", codeId],
    queryFn: () => getCodeInfo(codeId),
  });
};
