import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { queryClient } from "@/lib/react-query";

export type CreateCodeFeedbackDTO = {
  data: {
    codeId: number; // 리뷰를 등록하는 코드 id
    selectedRange: number[][]; // 선택된 구간 ex) [[1, 2], [6, 8]]
    codeContent: string; // 코드 내용 (base64 인코딩된 문자열)
    content: string; // 상세 설명 내용
  };
};

export const createCodeFeedback = ({
  data,
}: CreateCodeFeedbackDTO): Promise<number> => {
  return axios.post("/code/review", data);
};

export const useCreateCodeFeedback = () => {
  return useMutation({
    mutationFn: createCodeFeedback,
  });
};
