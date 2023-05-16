import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

const logout = (): Promise<null> => {
  return axios.post(`/user/logout`);
};

export const useLogout = () => {
  return useMutation({
    mutationFn: logout,
  });
};
