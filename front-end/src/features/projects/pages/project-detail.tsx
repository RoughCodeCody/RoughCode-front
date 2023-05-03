import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { FeedbackRegister, Feedbacks } from "@/features/feedbacks";

import { useProjectInfo } from "../api";
import { Notice } from "../components/notice";
import { ProjectDescription } from "../components/project-description";
import { ProjectInfo } from "../components/project-info";
import { RelatedCodes } from "../components/related-codes";
import { VersionsInfo } from "../components/versions-info";

type ProjectDetailProps = {
  projectId: string;
};

export const ProjectDetail = ({ projectId }: ProjectDetailProps) => {
  const { isLoading, data, isSuccess, isError } = useProjectInfo({ projectId });
  console.log(data);

  if (isLoading) return <>loading</>;

  const {
    closed,
    code,
    content,
    date,
    favorite,
    favoriteCnt,
    feedbackCnt,
    feedbacks,
    img,
    likeCnt,
    liked,
    mine,
    notice,
    tags,
    title,
    url,
    userName,
    version,
    versions,
  } = data;

  return (
    <FlexDiv direction="column" gap="3rem" padding="2rem 0">
      <Notice
        notice={notice}
        version={version}
        date={date}
        versions={versions}
      />

      <WhiteBoxNoshad width="65%" padding="2.25rem">
        <ProjectInfo data={data} projectId={projectId} />
        <VersionsInfo versions={versions} curVersionId={projectId} />
        <img
          src={img}
          alt="project"
          style={{
            width: "100%",
            aspectRatio: "3 / 2",
            objectFit: "cover",
            marginTop: "2.5rem",
          }}
        />
        <ProjectDescription content={content} />
        <RelatedCodes codes={code} />
      </WhiteBoxNoshad>

      <FeedbackRegister type="feedback" id={projectId} />
      <Feedbacks type="feedback" feedbacks={feedbacks} />
    </FlexDiv>
  );
};
