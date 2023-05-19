import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Tag } from "../types";

type Language = {
  languageId: number;
  name: string;
  cnt: number;
}[];

export const getLanguageTags = ({
  whichTag,
  tagKeyword,
}: {
  whichTag: string;
  tagKeyword: string;
}): Promise<Language> => {
  return axios.get(`/code/${whichTag}`, {
    params: {
      keyword: tagKeyword,
    },
  });
};

type QueryFnType = typeof getLanguageTags;

type UseLanguageTagsOptions = {
  whichTag: string;
  tagKeyword: string;
  config?: QueryConfig<QueryFnType>;
};

export const useLanguageTags = ({
  whichTag,
  tagKeyword,
  config,
}: UseLanguageTagsOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["languageTags", whichTag, tagKeyword],
    queryFn: () => getLanguageTags({ whichTag, tagKeyword }),
    ...config,
  });
};
