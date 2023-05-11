import {
  BottomHeader,
  FlexDiv,
  Title,
  WhiteBoxNoshad,
} from "@/components/elements";
import { Head } from "@/components/head";
import { ProjectFeedbacksSidebar } from "@/features/feedbacks";

import { usePostProject } from "../api";
import { ProjectUpdateForm } from "../components/project-update-form";
import { ProjectUpdateValues } from "../types";

export const ProjectRegister = () => {
  const postProjectMutation = usePostProject();

  const onSubmit = async (values: ProjectUpdateValues) => {
    const todo = await postProjectMutation.mutateAsync({ data: values });
    // console.log(todo);
  };

  return (
    <>
      <Head
        title="프로젝트 등록 | 프로젝트"
        description="개발새발 프로젝트 등록"
      />
      <BottomHeader locations={["프로젝트 등록"]} />
      <FlexDiv direction="row" align="flex-start" gap="3rem" padding="2rem 0">
        <WhiteBoxNoshad width="65%" padding="2.25rem">
          <Title title="프로젝트 등록" description="프로젝트를 등록합니다." />
          <ProjectUpdateForm projectId={-1} onSubmit={onSubmit} />
        </WhiteBoxNoshad>
        <ProjectFeedbacksSidebar />
      </FlexDiv>
    </>
  );
};
