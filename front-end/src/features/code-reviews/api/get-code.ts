import { useQuery } from "@tanstack/react-query";

import { axiosExternal } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Code } from "../types";

export const getCode = ({
  githubUrl,
}: {
  githubUrl: string;
}): Promise<Code> => {
  return axiosExternal.get(githubUrl);
};

type QueryFnType = typeof getCode;

type UseCodeOptions = {
  githubUrl: string;
  config?: QueryConfig<QueryFnType>;
};

export const useCode = ({ githubUrl, config }: UseCodeOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["code", githubUrl],
    queryFn: () => getCode({ githubUrl }),
    enabled: !!githubUrl,
    onSuccess: () => {},
    ...config,
  });
};
