import { useMutation } from "@tanstack/react-query";

import { axios } from "@/lib/axios";

export type CreateCodeReviewDTO = {
  data: {
    codeId: number; // 리뷰를 등록하는 코드 id
    selectedRange: number[][]; // 선택된 구간 ex) [[1, 2], [6, 8]]
    codeContent: string; // 코드 내용 (base64 인코딩된 문자열)
    content: string; // 상세 설명 내용
  };
};

export const createCodeReview = ({
  data,
}: CreateCodeReviewDTO): Promise<number> => {
  return axios.post("/code/review", data);
};

export const useCreateCodeReview = () => {
  return useMutation({
    mutationFn: createCodeReview,
  });
};
