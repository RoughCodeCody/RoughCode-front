import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

type SendEmailAddressDTO = {
  params: {
    email: string;
  };
};

const sendEmailAddress = ({ params }: SendEmailAddressDTO): Promise<null> => {
  return axios.post(`/mypage/email=${params}`);
};

type UseSendEmailAddressOptions = {
  email: string;
};

export const useSendEmailAddress = () => {
  return useMutation({
    mutationFn: sendEmailAddress,
  });
};
