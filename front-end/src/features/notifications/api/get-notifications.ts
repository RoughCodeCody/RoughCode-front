import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

import { Notification } from "../types";

export const getNotifications = (): Promise<Notification[]> => {
  return axios.get(`/mypage/alarm`);
};

type QueryFnType = typeof getNotifications;

export const useNotifications = () => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["notifications"],
    queryFn: () => getNotifications(),
  });
};
