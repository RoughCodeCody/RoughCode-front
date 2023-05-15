import { useRouter } from "next/router";

import { ModifyCodeReview } from "@/features/code-reviews";

const ReviewCreate = () => {
  // review를 작성할 모체 code의 ID
  const router = useRouter();
  let codeReviewId;
  if (router.query && router.query["review-id"]) {
    codeReviewId = +router.query["review-id"];
  }

  // /code-review/feedback/create/ 뒤에 params가 없는 입력일 경우
  if (codeReviewId === undefined) {
    return <>잘못된 접근입니다</>;
  }

  return (
    <div>
      <ModifyCodeReview codeReviewId={codeReviewId} />
    </div>
  );
};

export default ReviewCreate;
