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

// import { useMutation } from "@tanstack/react-query";

// import { axios } from "@/lib/axios";
// import { MutationConfig, queryClient } from "@/lib/react-query";

// const logout = (): Promise<null> => {
//   return axios.post(`/user/logout`);
// };

// type useLogoutOptions = {
//   config?: MutationConfig<typeof logout>;
// };

// export const useLogout = ({ config }: useLogoutOptions = {}) => {
//   return useMutation({
//     mutationFn: logout,
//     ...config,
//   });
// };
