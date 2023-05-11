import { Head } from "@/components/head";
import { useRouter } from "next/router";

import { ProjectUpdateForm } from "../components/project-update-form";
import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";

export const ProjectRegister = () => {
  const router = useRouter();

  return (
    <>
      <Head
        title="프로젝트 등록 | 프로젝트"
        description="개발새발 프로젝트 등록"
      />
      <FlexDiv direction="row" align="flex-start" gap="3rem" padding="2rem 0">
        <WhiteBoxNoshad width="65%" padding="2.25rem">
          <ProjectUpdateForm onSuccess={() => router.push("/project")} />
        </WhiteBoxNoshad>
        <WhiteBoxNoshad width="25%" padding="2.25rem">
          <div>hello</div>
        </WhiteBoxNoshad>
      </FlexDiv>
    </>
  );
};