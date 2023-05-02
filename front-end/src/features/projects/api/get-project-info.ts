import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

type ProjectInfoResult = {
  closed: boolean;
  codeId: number[];
  content: string;
  date: Date;
  favorite: boolean;
  favoriteCnt: number;
  feedbackCnt: number;
  feedbacks: [];
  img: string;
  likeCnt: number;
  liked: boolean;
  notice: string;
  tags: string[];
  title: string;
  url: string;
  userName: string;
  version: number;
  versions: {
    date: Date;
    notice: string;
    projectId: number;
    selectedFeedbacks: [];
    version: number;
  }[];
};

export const getProjectInfo = (
  projectId: string
): Promise<ProjectInfoResult> => {
  return axios.get(`/project/${projectId}`);
};

type QueryFnType = typeof getProjectInfo;

type UseProjectInfoOptions = {
  projectId: string;
  config?: QueryConfig<QueryFnType>;
};

export const useProjectInfo = ({
  projectId,
  config,
}: UseProjectInfoOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    ...config,
    queryKey: ["projectInfo", projectId],
    queryFn: () => getProjectInfo(projectId),
  });
};
