import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { Notice } from "../components/notice";
import { ProjectInfo } from "../components/project-info";
import { VersionsInfo } from "../components/versions-info";
import { ProjectDescription } from "../components/project-description";
import { RelatedCodes } from "../components/related-codes";
import { FeedbackResister } from "@/components/feedback-resister";
import { Feedbacks } from "@/components/feedbacks";

export const ProjectDetail = () => {
  // 더미데이터
  const feedbacks = [
    {
      user: "닉네임",
      isApplied: true,
      content: "즐겨찾기 기능이 있으면 좋을 거 같아요",
      isMine: false,
      isLiked: false,
      likeCnt: 13,
      createdAt: "23.04.17 13:40",
    },
    {
      user: "닉네임",
      isApplied: true,
      content: "로그아웃 할 때 확인 한 번 해 주는 게 좋을 듯",
      isMine: false,
      isLiked: false,
      likeCnt: 13,
      createdAt: "23.04.17 13:40",
    },
    {
      user: "이건내꺼",
      isApplied: false,
      content: "순서는 반영된 피드백, 그다음 내가 쓴 피드백, 그다음 시간 순",
      isMine: true,
      isLiked: false,
      likeCnt: 13,
      createdAt: "23.04.17 13:40",
    },
    {
      user: "익명12",
      isApplied: false,
      content: "버전1의 피드백",
      isMine: false,
      isLiked: false,
      likeCnt: 13,
      createdAt: "23.04.17 13:40",
    },
  ];

  return (
    <FlexDiv direction="column" gap="3rem" padding="2rem 0">
      <Notice />

      <WhiteBoxNoshad width="65%" padding="2.25rem">
        <ProjectInfo />
        <VersionsInfo />
        <ProjectDescription />
        <RelatedCodes />
      </WhiteBoxNoshad>

      <FeedbackResister type="feedback" />
      <Feedbacks feedbacks={feedbacks} />
    </FlexDiv>
  );
};
