import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig, queryClient } from "@/lib/react-query";

import { CodeUpdateValues } from "../types";

export type PutCodeDTO = {
  data: CodeUpdateValues;
};

export const putCode = ({ data }: PutCodeDTO): Promise<{ codeId: number }> => {
  return axios.put(`/code`, data);
};

type usePutCodeOptions = {
  config?: MutationConfig<typeof putCode>;
};

export const usePutCode = ({ config }: usePutCodeOptions = {}) => {
  return useMutation({
    // onMutate
    // onError
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["code"] });
    },
    ...config,
    mutationFn: putCode,
  });
};
