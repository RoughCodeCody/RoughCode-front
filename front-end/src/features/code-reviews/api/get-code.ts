import { useQuery } from "@tanstack/react-query";

import { axiosExternal } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Code } from "../types";

export const getCode = ({
  githubApiUrl,
}: {
  githubApiUrl: string;
}): Promise<Code> => {
  return axiosExternal.get(githubApiUrl);
};

type QueryFnType = typeof getCode;

type UseCodeOptions = {
  githubApiUrl: string;
  config?: QueryConfig<QueryFnType>;
};

export const useCode = ({ githubApiUrl, config }: UseCodeOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["code", githubApiUrl],
    queryFn: () => getCode({ githubApiUrl }),
    enabled: !!githubApiUrl,
    ...config,
  });
};
