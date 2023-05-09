import { Notification } from "../types";
import { axios } from "@/lib/axios";
import { ExtractFnReturnType } from "@/lib/react-query";
import { useQuery } from "@tanstack/react-query";

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
