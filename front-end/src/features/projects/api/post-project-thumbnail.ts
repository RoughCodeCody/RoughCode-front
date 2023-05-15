import { axios } from "@/lib/axios";

export const postProjectThumbnail = ({
  data,
  projectId,
}: {
  data: FormData;
  projectId: string;
}): Promise<null> => {
  return axios.post(`project/${projectId}/thumbnail`, data);
};
