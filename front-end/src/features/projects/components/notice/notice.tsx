import { NoticeAlarmItem, Text } from "@/components/elements";
import { VersionDropdown } from "./version-dropdown";
import { NoticeContent, NoticeTimeWrapper } from "./style";

export const Notice = () => {
  //더미데이터
  const noticeContent = "프로젝트 및 코드 즐겨찾기 기능이 추가되었습니다!";
  const currentVersion = "V2";
  const currentVersionCreatedAt = "23.03.02 13:00";
  const versions = [
    { version: "V2", createdAt: "23.03.02 13:00" },
    { version: "V1", createdAt: "23.03.01 13:00" },
  ];

  return (
    <NoticeAlarmItem>
      <VersionDropdown currentVersion={currentVersion} versions={versions} />

      <NoticeContent>
        <Text size="1.2rem" bold={true}>
          {noticeContent}
        </Text>
      </NoticeContent>

      <NoticeTimeWrapper>
        <Text size="0.8rem">{currentVersionCreatedAt.split(" ")[0]}</Text>
        <Text size="0.8rem">{currentVersionCreatedAt.split(" ")[1]}</Text>
      </NoticeTimeWrapper>
    </NoticeAlarmItem>
  );
};
