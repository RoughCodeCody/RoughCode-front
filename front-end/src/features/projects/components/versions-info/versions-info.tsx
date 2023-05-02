import { Accordion, FlexDiv } from "@/components/elements";

import { Version } from "../../types";
import { VersionInfo } from "./version-info";

type VersionsInfoProps = {
  versions: Version[];
  curVersionId: string;
};

export const VersionsInfo = ({ versions, curVersionId }: VersionsInfoProps) => {
  // 더미데이터
  const isMine = true;
  // const versions = [
  //   {
  //     version: "v2",
  //     notice: "프로젝트 및 코드 즐겨찾기 기능이 추가되었습니다!",
  //     current: true,
  //     feedbacks: [
  //       {
  //         user: "닉네임",
  //         content: "즐겨찾기 기능이 있으면 좋을 거 같아요",
  //       },
  //       {
  //         user: "익명10",
  //         content: "로그아웃 할 때 확인 한 번 해 주는 게 좋을 듯",
  //       },
  //     ],
  //   },
  //   {
  //     version: "v1",
  //     notice: "개발새발 런칭! 프로젝트와 코드를 공유해보세요",
  //     current: false,
  //     feedbacks: [],
  //   },
  // ];

  return (
    <Accordion
      title="버전별 업그레이드 정보"
      hasBtn={isMine}
      btnText="+ 새 버전 등록"
    >
      <FlexDiv direction="column" gap="1rem">
        {versions.map(({ version, notice, projectId, selectedFeedbacks }) => (
          <VersionInfo
            version={version}
            notice={notice}
            current={Boolean(projectId === parseInt(curVersionId))}
            feedbacks={selectedFeedbacks}
            key={projectId}
          />
        ))}
      </FlexDiv>
    </Accordion>
  );
};
