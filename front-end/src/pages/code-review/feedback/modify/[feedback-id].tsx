import { useRouter } from "next/router";

import { ModifyCodeFeedback } from "@/features/code-reviews";

const FeedbackCreate = () => {
  // feedback을 작성할 모체 code의 ID
  const router = useRouter();
  let feedbackId;
  if (router.query && router.query["feedback-id"]) {
    feedbackId = +router.query["feedback-id"];
  }

  // /code-review/feedback/create/ 뒤에 params가 없는 입력일 경우
  if (feedbackId === undefined) {
    return <>잘못된 접근입니다</>;
  }

  return (
    <div>
      <ModifyCodeFeedback feedbackId={feedbackId} />
    </div>
  );
};

export default FeedbackCreate;
