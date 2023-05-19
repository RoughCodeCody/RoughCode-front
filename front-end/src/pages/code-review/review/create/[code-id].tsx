import { useRouter } from "next/router";
import { Head } from "@/components/head";

import { CreateCodeFeedback } from "@/features/code-reviews";

const FeedbackCreate = () => {
  // feedback을 작성할 모체 code의 ID
  const router = useRouter();
  let codeId;
  if (router.query && router.query["code-id"]) {
    codeId = +router.query["code-id"];
  }

  // /code-review/feedback/create/ 뒤에 params가 없는 입력일 경우
  if (codeId === undefined) {
    return (
      <>
        <Head
          title="코드 리뷰 등록 | 코드 리뷰"
          description="개발새발 코드 리뷰"
        />
        잘못된 접근입니다
      </>
    );
  }

  return (
    <div>
      <Head
        title="코드 리뷰 등록 | 코드 리뷰"
        description="개발새발 코드 리뷰"
      />

      <CreateCodeFeedback codeId={codeId} />
    </div>
  );
};

export default FeedbackCreate;
