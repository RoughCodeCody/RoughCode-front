import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig, queryClient } from "@/lib/react-query";

export type PostProjectFeedbackDTO = {
  projectId: number;
  content: string;
};

export const postProjectFeedback = (
  data: PostProjectFeedbackDTO
): Promise<null> => {
  return axios.post(`/project/feedback`, data);
};

type UseCreateDiscussionOptions = {
  config?: MutationConfig<typeof postProjectFeedback>;
};

export const usePostProjectFeedback = () => {
  return useMutation({
    onSuccess: (_, newFeedback) =>
      queryClient.invalidateQueries(["projectInfo", newFeedback.projectId]),
    mutationFn: postProjectFeedback,
  });
};
