import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

export const checkProjectUrl = ({
  url,
}: {
  url: string;
}): Promise<boolean | null> => {
  return axios.get(`/project/check`, {
    params: {
      url,
    },
  });
};

type QueryFnType = typeof checkProjectUrl;

type UseCheckProjectUrlOptions = {
  url: string;
  config?: QueryConfig<QueryFnType>;
};

export const useCheckProjectUrl = ({
  url,
  config,
}: UseCheckProjectUrlOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["checkProjectUrl", url],
    queryFn: () => checkProjectUrl({ url }),
    ...config,
  });
};
