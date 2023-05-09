import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { FeedbackRegister, Feedbacks } from "@/features/feedbacks";
import { VersionsInfo } from "@/features/version-info";

import { useProjectInfo } from "../api";
import { Notice } from "../components/notice";
import { ProjectDescription } from "../components/project-description";
import { ProjectInfo } from "../components/project-info";
import { RelatedCodes } from "../components/related-codes";

type ProjectDetailProps = {
  projectId: string;
};

export const ProjectDetail = ({ projectId }: ProjectDetailProps) => {
  // 프로젝트 정보 가져오기
  const { isLoading, data } = useProjectInfo({ projectId });
  console.log(data);

  if (isLoading) return <>loading</>;

  return (
    <>
      <FlexDiv direction="column" gap="3rem" padding="2rem 0">
        {data && (
          <>
            <Notice
              notice={data.notice}
              version={data.version}
              date={data.date}
              versions={data.versions}
            />

            <WhiteBoxNoshad width="65%" padding="2.25rem">
              <ProjectInfo data={data} projectId={projectId} />
              <VersionsInfo versions={data.versions} curVersionId={projectId} />
              <img
                src={data.img}
                alt="project"
                style={{
                  width: "100%",
                  aspectRatio: "3 / 2",
                  objectFit: "cover",
                  marginTop: "2.5rem",
                }}
              />
              <ProjectDescription content={data.content} />
              <RelatedCodes
                codes={data.code}
                isMine={data.mine}
                projectId={projectId}
              />
            </WhiteBoxNoshad>

            <FeedbackRegister type="feedback" id={projectId} />
            <Feedbacks type="feedback" feedbacks={data.feedbacks} />
          </>
        )}
      </FlexDiv>
    </>
  );
};
