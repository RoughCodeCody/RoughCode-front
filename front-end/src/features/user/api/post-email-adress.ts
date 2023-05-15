import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

const sendEmailAddress = (email: string): Promise<null> => {
  return axios.post(`/mypage/email?email=${email}`);
};

export const useSendEmailAddress = () => {
  return useMutation({
    mutationKey: ["email"],
    mutationFn: sendEmailAddress,
  });
};
