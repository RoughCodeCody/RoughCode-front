import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

type ProjectInfoResult = {};

export const checkURLOpen = (projectId: string): Promise<ProjectInfoResult> => {
  return axios.put(`/project/check/${projectId}`);
};

export const useCheckURLOpen = () => {
  return useMutation({
    mutationFn: checkURLOpen,
  });
};
