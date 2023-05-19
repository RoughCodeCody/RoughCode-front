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
import { usePostCode } from "../api/post-code";
import { CodeReviewSidebar } from "../components/code-review-sidebar";
import { CodeUpdateForm } from "../components/code-update-form";
import { CodeUpdateValues, CodeLanguage } from "../types";
import { convertGitHubUrl } from "@/util/convert-github-url";

export const CodeUpgrade = ({ codeId }: { codeId: string }) => {
  const codeIdNum = Number(codeId);
  const router = useRouter();
  const postCodeMutation = usePostCode();
  const codeInfoQuery = useCodeInfo(codeIdNum);

  if (!codeInfoQuery.data) {
    return <>Loading...</>;
  }
  const codeUpdateInitialValues = {
    title: codeInfoQuery.data?.title || "",
    githubUrl: codeInfoQuery.data?.githubUrl || "",
    content: codeInfoQuery.data?.content || "",
    codeId: codeIdNum,
    projectId: codeInfoQuery.data?.projectId || null,
    selectedTagsId: codeInfoQuery.data?.tags.map((tag) => Number(tag.tagId)),
    selectedReviewsId: codeInfoQuery.data?.versions
      .filter((el) => el.version === codeInfoQuery.data?.version)[0]
      .selectedReviews.map((el) => el.reviewId),
    language: [codeInfoQuery.data?.language.languageId],
    languages: [codeInfoQuery.data?.language],
    tags: codeInfoQuery.data?.tags,
  };

  const onSubmit = async (values: CodeUpdateValues) => {
    const newValues = {
      ...values,
      githubUrl: convertGitHubUrl(values.githubUrl),
    };
    const codeIdNum = await postCodeMutation.mutateAsync({
      data: newValues,
    });
    const codeIdStr = String(codeIdNum);
    router.push(`/code-review/${codeIdStr}`);
  };

  return (
    <>
      <Head title="코드 버전 업 | 코드 리뷰" description="개발새발 코드 수정" />
      <BottomHeader locations={["코드 버전 업"]} />
      <FlexDiv direction="row" align="flex-start" gap="3rem" padding="2rem 0">
        <WhiteBoxNoshad width="65%" padding="2.25rem">
          <Title
            title="코드 버전 업"
            description="코드의 버전을 업그레이드합니다."
          />
          <CodeUpdateForm
            codeId={codeIdNum}
            onSubmit={onSubmit}
            codeUpdateInitialValues={codeUpdateInitialValues}
          />
        </WhiteBoxNoshad>
        <CodeReviewSidebar codeId={codeId} versionUp="false" />
      </FlexDiv>
    </>
  );
};
