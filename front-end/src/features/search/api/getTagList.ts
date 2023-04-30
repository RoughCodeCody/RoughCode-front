import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Tag } from "../types";

export const getTags = ({
  tagKeyword,
}: {
  tagKeyword: string;
}): Promise<Tag[]> => {
  return axios.get(`/project/tag`, {
    params: {
      keyword: tagKeyword,
    },
  });
};

type QueryFnType = typeof getTags;

type UseTagsOptions = {
  tagKeyword: string;
  config?: QueryConfig<QueryFnType>;
};

export const useTags = ({ tagKeyword, config }: UseTagsOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["tags", tagKeyword],
    queryFn: () => getTags({ tagKeyword }),
    ...config,
  });
};
