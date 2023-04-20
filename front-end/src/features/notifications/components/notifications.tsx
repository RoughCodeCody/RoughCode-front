import { NoticeAlarmItem } from "@/components/notice-alarm-item";
import {
  HomeContainer,
  HomeContentsContainer,
  TitleContainer,
  Title,
  Description,
  NoticeContainer,
} from "./notifications-style";

export const Notifications = () => {
  const notice: object[] = [
    { 정민: "code" },
    { 수연: "pjt" },
    { 동수: "code" },
    { 하늬: "pjt" },
    { 정훈: "code" },
    { 준하: "pjt" },
    { 정민: "code" },
    { 수연: "pjt" },
    { 동수: "code" },
    { 하늬: "pjt" },
    { 정훈: "code" },
    { 준하: "pjt" },
    { 정민: "code" },
    { 수연: "pjt" },
    { 동수: "code" },
    { 하늬: "pjt" },
    { 정훈: "code" },
    { 준하: "pjt" },
    { 정민: "code" },
    { 수연: "pjt" },
    { 동수: "code" },
    { 하늬: "pjt" },
    { 정훈: "code" },
    { 준하: "pjt" },
    { 정민: "code" },
    { 수연: "pjt" },
    { 동수: "code" },
    { 하늬: "pjt" },
    { 정훈: "code" },
    { 준하: "pjt" },
  ];

  return (
    <>
      <HomeContainer>
        <HomeContentsContainer>
          <TitleContainer>
            <Title>새로운피드</Title>
            <Description>
              나의 피드백을 반영한 코드와 프로젝트를 확인해 보세요
            </Description>
          </TitleContainer>
          <NoticeContainer>
            {notice.map((names: object) => (
              <NoticeAlarmItem
                width={"100%"}
                isDirectionRight={
                  Object.values(names)[0] === "pjt" ? true : false
                }
                color={Object.values(names)[0] === "pjt" ? "sub-three" : ""}
                shadow={true}
                key={Object.keys(names)[0]}
              >
                {Object.keys(names)}
              </NoticeAlarmItem>
            ))}
          </NoticeContainer>
        </HomeContentsContainer>
      </HomeContainer>
    </>
  );
};
