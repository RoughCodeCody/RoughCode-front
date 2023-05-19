import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Tag } from "../types";

export const getTags = ({
  whichTag,
  tagKeyword,
}: {
  whichTag: string;
  tagKeyword: string;
}): Promise<Tag> => {
  return axios.get(`/${whichTag}/tag`, {
    params: {
      keyword: tagKeyword,
    },
  });
};

type QueryFnType = typeof getTags;

type UseTagsOptions = {
  whichTag: string;
  tagKeyword: string;
  config?: QueryConfig<QueryFnType>;
};

export const useTags = ({ whichTag, tagKeyword, config }: UseTagsOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["tags", whichTag, tagKeyword],
    queryFn: () => getTags({ whichTag, tagKeyword }),
    ...config,
  });
};
