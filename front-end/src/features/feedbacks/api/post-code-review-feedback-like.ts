import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig } from "@/lib/react-query";

export const postCodeReviewFeedbackLike = (
  feedbackId: number
): Promise<null> => {
  return axios.post(`/code/rereview/${feedbackId}/like`);
};

type usePostCodeReviewFeedbackLikeOptions = {
  config?: MutationConfig<typeof postCodeReviewFeedbackLike>;
};

export const usePostCodeReviewFeedbackLike = ({
  config,
}: usePostCodeReviewFeedbackLikeOptions = {}) => {
  return useMutation({
    ...config,
    mutationFn: postCodeReviewFeedbackLike,
  });
};

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
