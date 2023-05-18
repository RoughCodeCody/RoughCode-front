import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { MutationConfig, queryClient } from "@/lib/react-query";

import { CodeUpdateValues } from "../types";

export type PutCodeDTO = {
  data: Omit<CodeUpdateValues, "codeId">;
  codeId: number;
};

export const putCode = ({ data, codeId }: PutCodeDTO): Promise<null> => {
  return axios.put(`/code/${codeId}`, data);
};

type UsePutCodeOptions = {
  config?: MutationConfig<typeof putCode>;
};

export const usePutCode = ({ config }: UsePutCodeOptions = {}) => {
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
