import type { MutationConfig } from "@/lib/react-query";

import { useMutation } from "@tanstack/react-query";
import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

type PostProjectThumbnailDTO = {
  data: FormData;
  projectId: string;
};

export const postProjectThumbnail = ({
  data,
  projectId,
}: PostProjectThumbnailDTO): Promise<null> => {
  return axios.post(`project/${projectId}/thumbnail`, data);
};

type UsePostProjectThumbnailOptions = {
  projectId: string;
  config?: MutationConfig<typeof postProjectThumbnail>;
};

export const usePostProjectThumbnail = ({ projectId, config }: UsePostProjectThumbnailOptions) => {
  return useMutation({
    onSuccess: () => {
      queryClient.invalidateQueries(["projectInfo", projectId]);
    },
    ...config,
    mutationFn: postProjectThumbnail,
  });
};
