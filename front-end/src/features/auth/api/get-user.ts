import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { User } from "../types";

export const getUser = (): Promise<User[]> => {
  return axios.get(`/user`);
};

type QueryFnType = typeof getUser;

type UseUserOptions = {
  config?: QueryConfig<QueryFnType>;
};

export const useUser = ({ config = {} }: UseUserOptions = {}) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["user"],
    queryFn: () => getUser(),
    ...config,
  });
};
