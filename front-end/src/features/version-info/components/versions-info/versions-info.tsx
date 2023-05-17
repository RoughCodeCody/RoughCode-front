import { Accordion, FlexDiv } from "@/components/elements";
import { CodeVersion } from "@/features/code-reviews";
import { ProjectVersion } from "@/features/projects";

import { VersionInfo } from "./version-info";
import { useRouter } from "next/router";

type VersionsInfoProps = {
  versions: ProjectVersion[] | CodeVersion[];
  curVersionId: string;
  isMine: boolean;
};

// 프로젝트 버전인지 여부를 판별
function isProject(arg: any): arg is ProjectVersion[] {
  return arg?.[0].projectId !== undefined;
}

// 코드 버전인지 여부를 판별
function isCode(arg: any): arg is CodeVersion[] {
  return arg?.[0].codeId !== undefined;
}

export const VersionsInfo = ({
  versions,
  curVersionId,
  isMine,
}: VersionsInfoProps) => {
  const router = useRouter();

  return (
    <Accordion
      title="버전별 업그레이드 정보"
      hasBtn={isMine}
      btnText="+ 새 버전 등록"
      btnClickFunc={() => {
        const path = isProject(versions) ? "project" : "code-review";
        router.push(`/${path}/upgrade/${curVersionId}`);
      }}
    >
      {isProject(versions) ? (
        <>
          <FlexDiv direction="column" gap="1rem">
            {versions.map(
              ({ version, notice, projectId, selectedFeedbacks }) => (
                <VersionInfo
                  version={version}
                  title={notice}
                  current={Boolean(projectId === parseInt(curVersionId))}
                  feedbacks={selectedFeedbacks}
                  id={projectId}
                  type="project"
                  key={projectId}
                />
              )
            )}
          </FlexDiv>
        </>
      ) : isCode(versions) ? (
        <>
          <FlexDiv direction="column" gap="1rem">
            {versions.map(({ version, codeId, selectedReviews }) => (
              <VersionInfo
                version={version}
                title={"title"}
                current={Boolean(codeId === parseInt(curVersionId))}
                feedbacks={selectedReviews}
                id={codeId}
                type="code-review"
                key={codeId}
              />
            ))}
          </FlexDiv>
        </>
      ) : (
        <></>
      )}
    </Accordion>
  );
};
