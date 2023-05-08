import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { CodeInfo } from "../types";

export const getCodeInfo = ({
  codeId,
}: {
  codeId: number;
}): Promise<CodeInfo> => {
  return axios.get(`/code/${codeId}`);
};

type QueryFnType = typeof getCodeInfo;

type UseCodeInfoOptions = {
  codeId: number;
  config?: QueryConfig<QueryFnType>;
};

export const useCodeInfo = ({ codeId, config }: UseCodeInfoOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["codeInfo", codeId],
    queryFn: () => getCodeInfo({ codeId }),
    onSuccess: () => {},
    ...config,
  });
};
