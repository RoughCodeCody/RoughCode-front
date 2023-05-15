import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig, queryClient } from "@/lib/react-query";

import { ProjectInfoResult } from "../types";

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

export const usePostProjectFeedback = ({
  config,
}: UseCreateDiscussionOptions = {}) => {
  return useMutation({
    // onMutate: async (newFeedback) => {
    //   await queryClient.cancelQueries([
    //     "projectInfo",
    //     newFeedback.data.projectId,
    //   ]);

    //   const previousProjectInfo = queryClient.getQueryData<ProjectInfoResult>([
    //     "projectInfo",
    //     newFeedback.data.projectId,
    //   ]);

    //   queryClient.setQueryData(["projectInfo", newFeedback.data.projectId], {
    //     ...previousProjectInfo,
    //     feedbacks: [
    //       ...(previousProjectInfo?.feedbacks || []),
    //       newFeedback.data,
    //     ],
    //   });

    //   return { previousProjectInfo };
    // },
    // onError: (_, newFeedback, context: any) => {
    //   if (context?.previousFeedbacks) {
    //     queryClient.setQueryData(
    //       ["projectInfo", newFeedback.data.projectId],
    //       context.previousFeedbacks
    //     );
    //   }
    // },
    ...config,
    mutationFn: postProjectFeedback,
  });
};
