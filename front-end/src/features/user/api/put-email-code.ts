import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

type sendEmailCodeDTO = {
  email: string | undefined;
  code: string;
};

const sendEmailCode = ({ email, code }: sendEmailCodeDTO): Promise<null> => {
  return axios.put(`/mypage/email?email=${email}&code=${code}`);
};

export const useSendEmailCode = () => {
  return useMutation({
    mutationFn: sendEmailCode,
  });
};
