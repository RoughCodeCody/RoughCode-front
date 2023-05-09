import { useNotifications } from "../api/get-notifications";
import { NoticeContent } from "../components";
import {
  HomeContainer,
  HomeContentsContainer,
  NoticeContainer,
} from "./notifications-style";
import { Notification } from "../types";
import { NoticeAlarmItem, Title } from "@/components/elements";

export const Notifications = () => {
  const notificationsQuery = useNotifications();
  console.log(notificationsQuery.data);

  if (notificationsQuery.isLoading) {
    return <>loading...</>;
  }

  if (notificationsQuery.isError) {
    return <>Not Found</>;
  }

  return (
    <>
      <HomeContainer>
        <HomeContentsContainer>
          {notificationsQuery.data.length ? (
            <>
              <Title
                title="새로운 피드"
                description="나의 피드백을 반영한 코드와 프로젝트를 확인해 보세요"
              />
              <NoticeContainer>
                {notificationsQuery.data.map((notice: Notification) => (
                  <NoticeAlarmItem
                    width={"100%"}
                    isNotice={true}
                    isDirectionRight={
                      notice.section === "project" ? true : false
                    }
                    color={notice.section === "project" ? "sub-three" : ""}
                    shadow={true}
                    alarmId={notice.alarmId}
                    key={notice.alarmId}
                  >
                    <NoticeContent data={notice}></NoticeContent>
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
