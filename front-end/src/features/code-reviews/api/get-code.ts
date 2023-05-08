import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Code } from "../types";

export const getCode = ({ codeId }: { codeId: number }): Promise<Code> => {
  return axios.get("/code", {
    params: {
      codeId: codeId,
    },
  });
};

type QueryFnType = typeof getCode;

type UseCodeOptions = {
  codeId: number;
  config?: QueryConfig<QueryFnType>;
};

export const useCode = ({ codeId, config }: UseCodeOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["code", codeId],
    queryFn: () => getCode({ codeId }),
    ...config,
  });
};
