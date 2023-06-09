import { useRouter } from "next/router";

import {
  BottomHeader,
  FlexDiv,
  Title,
  WhiteBoxNoshad,
} from "@/components/elements";
import { Head } from "@/components/head";
import { ProjectFeedbacksSidebar } from "@/features/feedbacks";

import { postProjectThumbnail, usePutProject, useProjectInfo } from "../api";
import { ProjectUpdateForm } from "../components/project-update-form";
import { ProjectUpdateValues } from "../types";

export const ProjectModify = ({ projectId }: { projectId: string }) => {
  const projectIdNum = Number(projectId);
  const router = useRouter();
  const putProjectMutation = usePutProject();
  const projectInfoQuery = useProjectInfo({ projectId: projectIdNum });

  if (!projectInfoQuery.data) {
    return <>Loading...</>;
  }
  const projectUpdateInitialValues = {
    title: projectInfoQuery.data?.title || "",
    notice: projectInfoQuery.data?.notice || "",
    introduction: projectInfoQuery.data?.introduction || "",
    content: projectInfoQuery.data?.content || "",
    url: projectInfoQuery.data?.url || "",
    projectId: projectIdNum,
    selectedTagsId: projectInfoQuery.data?.tags.map((tag) => tag.tagId) || [],
    selectedFeedbacksId:
      projectInfoQuery.data?.feedbacks.map((feedback) =>
        Number(feedback.feedbackId)
      ) || [],
    img: projectInfoQuery.data?.img,
    tags: projectInfoQuery.data?.tags,
  };

  const onSubmit = async (values: ProjectUpdateValues) => {
    await putProjectMutation.mutateAsync({
      data: values,
    });

    const inputThumbnail = document.getElementById(
      "input-thumbnail"
    ) as HTMLInputElement;

    const formData = new FormData();
    const thumbnail = inputThumbnail?.files?.item(0) as File;
    if (thumbnail !== null) {
      formData.append("thumbnail", thumbnail);

      await postProjectThumbnail({
        data: formData,
        projectId: projectId,
      });
    }

    router.push(`/project/${projectId}`);
  };

  return (
    <>
      <Head
        title="프로젝트 수정 | 프로젝트"
        description="개발새발 프로젝트 수정"
      />
      <BottomHeader locations={["프로젝트 수정"]} />
      <FlexDiv direction="row" align="flex-start" gap="3rem" padding="2rem 0">
        <WhiteBoxNoshad width="65%" padding="2.25rem">
          <Title title="프로젝트 수정" description="프로젝트를 수정합니다." />
          <ProjectUpdateForm
            projectId={projectIdNum}
            onSubmit={onSubmit}
            projectUpdateInitialValues={projectUpdateInitialValues}
          />
        </WhiteBoxNoshad>
        <ProjectFeedbacksSidebar projectId={projectId} versionUp="false" />
      </FlexDiv>
    </>
  );
};
