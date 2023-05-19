import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Review } from "../types";

export const getCodeReviewsForCodeUpdate = ({
  codeId,
  versionUp,
}: {
  codeId: string;
  versionUp: string;
}): Promise<Review[]> => {
  return axios.get(`/code/${codeId}/review`, { params: { versionUp } });
};

type QueryFnType = typeof getCodeReviewsForCodeUpdate;

type UseCodeReviewsForCodeUpdateOptions = {
  codeId: string;
  versionUp: string;
  config?: QueryConfig<QueryFnType>;
};

export const useCodeReviewsForCodeUpdate = ({
  codeId,
  versionUp,
  config,
}: UseCodeReviewsForCodeUpdateOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["codeReviewsForCodeUpdate", codeId, versionUp],
    queryFn: () => getCodeReviewsForCodeUpdate({ codeId, versionUp }),
    ...config,
  });
};
