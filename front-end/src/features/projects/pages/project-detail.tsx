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
  const { isLoading, data } = useProjectInfo({ projectId: Number(projectId) });

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

            <WhiteBoxNoshad
              width="60%"
              padding="2.25rem"
              style={{ minWidth: "850px" }}
            >
              <ProjectInfo
                data={data}
                projectId={projectId}
                isLatest={Boolean(data.version === data.versions.length)}
              />
              <VersionsInfo
                versions={data.versions}
                curVersionId={projectId}
                isMine={data.mine}
              />
              <img
                src={data.img}
                alt="project image"
                style={{
                  width: "100%",
                  objectFit: "contain",
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

            <FeedbackRegister type="project" id={Number(projectId)} />
            <Feedbacks
              type="project"
              feedbacks={data.feedbacks}
              projectOrCodeId={Number(projectId)}
            />
          </>
        )}
      </FlexDiv>
    </>
  );
};
