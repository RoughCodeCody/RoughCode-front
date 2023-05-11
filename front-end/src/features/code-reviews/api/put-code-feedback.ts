import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

// 생성 수정 모두 같은 타입이라서 수정하는 요청이지만 공유해서 사용
export type ModifyCodeFeedbackDTO = {
  feedbackId: number;
  data: {
    codeId: number; // 리뷰를 등록하는 코드 id
    selectedRange: number[][]; // 선택된 구간 ex) [[1, 2], [6, 8]]
    codeContent: string; // 코드 내용 (base64 인코딩된 문자열)
    content: string; // 상세 설명 내용
  };
};

export const modifyCodeFeedback = ({
  feedbackId,
  data,
}: ModifyCodeFeedbackDTO): Promise<null> => {
  return axios.put(`/code/review/${feedbackId}`, data);
};

export const useModifyCodeFeedback = () => {
  return useMutation({
    mutationFn: modifyCodeFeedback,
  });
};
