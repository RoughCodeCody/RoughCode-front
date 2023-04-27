import { NoticeAlarmItem, Title } from "@/components/elements";
import { NoticeContent } from "../components";
import {
  HomeContainer,
  HomeContentsContainer,
  NoticeContainer,
} from "./notifications-style";

// 피드 불러오기
// 피드 삭제 시 서버에서도 지우나?

interface notice {
  writer: string;
  isProject: boolean;
  title: string;
  version: number;
}

export const Notifications = () => {
  const notice: notice[] = [
    {
      writer: "정민",
      isProject: true,
      title: "이 코드 어떻게 작동시키져",
      version: 4,
    },
    { writer: "수연", isProject: false, title: "싸피", version: 2 },
    { writer: "동수", isProject: false, title: "개발새발", version: 3 },
    {
      writer: "하늬",
      isProject: true,
      title: "저 코드 어떻게 작동시키져",
      version: 5,
    },
    { writer: "정훈", isProject: false, title: "조기퇴근", version: 2 },
    {
      writer: "준하",
      isProject: true,
      title: "이 코드 어떻게 작동시키져",
      version: 2,
    },
  ];
  console.log(notice);
  return (
    <>
      <HomeContainer>
        <HomeContentsContainer>
          {notice.length ? (
            <>
              <Title
                title="새로운 피드"
                description="나의 피드백을 반영한 코드와 프로젝트를 확인해 보세요"
              />
              <NoticeContainer>
                {notice.map((notice: notice) => (
                  <NoticeAlarmItem
                    width={"100%"}
                    isNotice={true}
                    isDirectionRight={notice.isProject}
                    color={notice.isProject ? "sub-three" : ""}
                    shadow={true}
                    key={notice.title}
                  >
                    <NoticeContent
                      writer={notice.writer}
                      isProject={notice.isProject}
                      title={notice.title}
                      version={notice.version}
                    ></NoticeContent>
                  </NoticeAlarmItem>
                ))}
              </NoticeContainer>
            </>
          ) : (
            <Title
              title="새로운 피드가 없어요"
              description="더 많은 피드백을 달아주세요"
            />
          )}
        </HomeContentsContainer>
      </HomeContainer>
    </>
  );
};
