import { useRouter } from "next/router";

import {
  BottomHeader,
  FlexDiv,
  Title,
  WhiteBoxNoshad,
} from "@/components/elements";
import { Head } from "@/components/head";
import { ProjectFeedbacksSidebar } from "@/features/feedbacks";

import { postProjectThumbnail, usePostProject, useProjectInfo } from "../api";
import { ProjectUpdateForm } from "../components/project-update-form";
import { ProjectUpdateValues } from "../types";

export const ProjectUpgrade = ({ projectId }: { projectId: string }) => {
  const projectIdNum = Number(projectId);
  const router = useRouter();
  const postProjectMutation = usePostProject();
  const projectInfoQuery = useProjectInfo({ projectId: projectIdNum });

  if (!projectInfoQuery.data) {
    return <>Loading...</>;
  }

  const projectUpdateInitialValues = {
    title: projectInfoQuery.data?.title || "",
    notice: "",
    introduction: projectInfoQuery.data?.introduction || "",
    content: "",
    url: projectInfoQuery.data?.url || "",
    projectId: Number(projectId),
    selectedTagsId: projectInfoQuery.data?.tags.map((tag) => tag.tagId) || [],
    selectedFeedbacksId:
      projectInfoQuery.data?.feedbacks.map((feedback) =>
        Number(feedback.feedbackId)
      ) || [],
    img: "",
    tags: projectInfoQuery.data?.tags,
  };

  const onSubmit = async (values: ProjectUpdateValues) => {
    const resProjectIdNum = await postProjectMutation.mutateAsync({
      data: values,
    });
    const resProjectIdStr = String(resProjectIdNum);

    const inputThumbnail = document.getElementById(
      "input-thumbnail"
    ) as HTMLInputElement;

    const formData = new FormData();
    const thumbnail = inputThumbnail?.files?.item(0) as File;
    if (thumbnail !== null) {
      formData.append("thumbnail", thumbnail);

      await postProjectThumbnail({
        data: formData,
        projectId: resProjectIdStr,
      });
    }

    router.push(`/project/${resProjectIdStr}`);
  };

  return (
    <>
      <Head
        title="프로젝트 버전 업 | 프로젝트"
        description="개발새발 프로젝트 버전 업"
      />
      <BottomHeader locations={["프로젝트 버전 업"]} />
      <FlexDiv direction="row" align="flex-start" gap="3rem" padding="2rem 0">
        <WhiteBoxNoshad width="65%" padding="2.25rem">
          <Title
            title="프로젝트 버전 업"
            description="프로젝트의 버전을 올립니다."
          />
          <ProjectUpdateForm
            projectId={projectIdNum}
            onSubmit={onSubmit}
            projectUpdateInitialValues={projectUpdateInitialValues}
          />
        </WhiteBoxNoshad>
        <ProjectFeedbacksSidebar projectId={projectId} versionUp="true" />
      </FlexDiv>
    </>
  );
};
