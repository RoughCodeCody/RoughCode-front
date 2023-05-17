import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Review } from "../types";

export const getCodeReviewsForCodeUpdate = ({
  codeId,
}: {
  codeId: string;
}): Promise<Review[]> => {
  return axios.get(`/code/${codeId}/review`);
};

type QueryFnType = typeof getCodeReviewsForCodeUpdate;

type UseCodeReviewsForCodeUpdateOptions = {
  codeId: string;
  config?: QueryConfig<QueryFnType>;
};

export const useCodeReviewsForCodeUpdate = ({
  codeId,
  config,
}: UseCodeReviewsForCodeUpdateOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["codeReviewsForCodeUpdate", codeId],
    queryFn: () => getCodeReviewsForCodeUpdate({ codeId }),
    ...config,
  });
};
