import { useRouter } from "next/router";
import { useEffect } from "react";

import {
  BottomHeader,
  FlexDiv,
  Title,
  WhiteBoxNoshad,
} from "@/components/elements";
import { Head } from "@/components/head";
import { useSearchCriteriaStore } from "@/stores";

import { useCodeInfo } from "../api/get-code-info";
import { usePutCode } from "../api/put-code";
import { CodeReviewSidebar } from "../components/code-review-sidebar";
import { CodeUpdateForm } from "../components/code-update-form";
import { CodeUpdateValues } from "../types";

export const CodeModify = ({ codeId }: { codeId: string }) => {
  const codeIdNum = Number(codeId);
  const router = useRouter();
  const putCodeMutation = usePutCode();
  const codeInfoQuery = useCodeInfo(codeIdNum);
  const { language, setLanguage } = useSearchCriteriaStore();
  useEffect(() => {
    if (codeInfoQuery.data) {
      setLanguage(codeInfoQuery.data.tags);
    }
  }, [codeInfoQuery.data]);

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

  console.log(codeInfoQuery.data);
  const onSubmit = async (values: CodeUpdateValues) => {
    const codeIdNum = await putCodeMutation.mutateAsync({
      data: values,
    });
    const codeIdStr = String(codeIdNum);
    router.push(`/code-review/${codeIdStr}`);
  };

  return (
    <>
      <Head title="코드 수정 | 코드 리뷰" description="개발새발 코드 수정" />
      <BottomHeader locations={["코드 수정"]} />
      <FlexDiv direction="row" align="flex-start" gap="3rem" padding="2rem 0">
        <WhiteBoxNoshad width="65%" padding="2.25rem">
          <Title title="코드 수정" description="코드를 수정합니다." />
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
