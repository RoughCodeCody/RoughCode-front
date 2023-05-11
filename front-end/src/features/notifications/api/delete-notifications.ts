import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";
import { useMutation } from "@tanstack/react-query";

// 생성 수정 모두 같은 타입이라서 수정하는 요청이지만 공유해서 사용

export const deleteNotification = (alarmId: string): Promise<null> => {
  return axios.delete(`/mypage/alarm/${alarmId}`);
};

export const useDeleteNotification = () => {
  return useMutation({
    onSuccess: () =>
      queryClient.invalidateQueries({
        queryKey: ["notifications"],
      }),

    mutationFn: deleteNotification,
  });
};
