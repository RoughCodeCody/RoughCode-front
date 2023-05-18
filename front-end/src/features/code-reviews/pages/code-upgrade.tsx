import { useRouter } from "next/router";

import {
  BottomHeader,
  FlexDiv,
  Title,
  WhiteBoxNoshad,
} from "@/components/elements";
import { Head } from "@/components/head";

import { useCodeInfo } from "../api/get-code-info";
import { usePostCode } from "../api/post-code";
import { CodeReviewSidebar } from "../components/code-review-sidebar";
import { CodeUpdateForm } from "../components/code-update-form";
import { CodeUpdateValues } from "../types";

export const CodeUpgrade = ({ codeId }: { codeId: string }) => {
  const codeIdNum = Number(codeId);
  const router = useRouter();
  const postCodeMutation = usePostCode();
  const codeInfoQuery = useCodeInfo(codeIdNum);

  if (!codeInfoQuery.data) {
    return <>Loading...</>;
  }

  const codeUpdateInitialValues = {
    title: codeInfoQuery.data?.title,
    githubUrl: codeInfoQuery.data?.githubUrl,
    content: codeInfoQuery.data?.content,
    codeId: codeInfoQuery.data?.codeId,
    projectId: codeInfoQuery.data?.projectId,
    selectedTagsId: null,
    selectedReviewsId: codeInfoQuery.data?.reviews.map((review) =>
      Number(review.reviewId)
    ),
    language: codeInfoQuery.data?.language,
  };

  const onSubmit = async (values: CodeUpdateValues) => {
    const codeIdNum = await postCodeMutation.mutateAsync({
      data: values,
    });
    const codeIdStr = String(codeIdNum);
    router.push(`/code-review/${codeIdStr}`);
  };

  return (
    <>
      <Head
        title="코드 버전 업 | 코드 리뷰"
        description="개발새발 코드 버전 업"
      />
      <BottomHeader locations={["코드 버전 업"]} />
      <FlexDiv direction="row" align="flex-start" gap="3rem" padding="2rem 0">
        <WhiteBoxNoshad width="65%" padding="2.25rem">
          <Title title="코드 버전 업" description="코드를 수정합니다." />
          <CodeUpdateForm
            codeId={codeIdNum}
            onSubmit={onSubmit}
            codeUpdateInitialValues={codeUpdateInitialValues}
          />
        </WhiteBoxNoshad>
        <CodeReviewSidebar />
      </FlexDiv>
    </>
  );
};
