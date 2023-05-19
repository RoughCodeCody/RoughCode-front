import { useRouter } from "next/router";
import { Head } from "@/components/head";

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
    return (
      <>
        <Head
          title="코드 리뷰 수정 | 코드 리뷰"
          description="개발새발 코드 리뷰"
        />
        잘못된 접근입니다
      </>
    );
  }

  return (
    <div>
      <Head
        title="코드 리뷰 수정 | 코드 리뷰"
        description="개발새발 코드 리뷰"
      />
      <ModifyCodeReview codeReviewId={codeReviewId} />
    </div>
  );
};

export default ReviewCreate;
