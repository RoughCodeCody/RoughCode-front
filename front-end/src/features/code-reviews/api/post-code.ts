import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig, queryClient } from "@/lib/react-query";

import { CodeUpdateValues } from "../types";

export type PostCodeDTO = {
  data: CodeUpdateValues;
};

export const postCode = ({
  data,
}: PostCodeDTO): Promise<{ codeId: number }> => {
  return axios.post(`/code`, data);
};

type usePostCodeOptions = {
  config?: MutationConfig<typeof postCode>;
};

export const usePostCode = ({ config }: usePostCodeOptions = {}) => {
  return useMutation({
    // onMutate
    // onError
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["code"] });
    },
    ...config,
    mutationFn: postCode,
  });
};
