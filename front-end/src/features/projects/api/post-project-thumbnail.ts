// import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
// import { MutationConfig, queryClient } from "@/lib/react-query";

type ProjectThumbnailValues = {
  thumbnail: FormData;
};

export const postProjectThumbnail = ({
  data,
  projectId,
}: {
  data: ProjectThumbnailValues;
  projectId: number;
}): Promise<null> => {
  return axios.post(`project/thumbnail`, data, { params: { projectId } });
};

// type usePostProjectThumbnailOptions = {
//   config?: MutationConfig<typeof postProjectThumbnail>;
// };

// export const usePostProjectThumbnail = ({ config }: usePostProjectThumbnailOptions = {}) => {
//   return useMutation({
//     // onMutate
//     // onError
//     // onSuccess
//     ...config,
//     mutationFn: PostProject,
//   });
// };
