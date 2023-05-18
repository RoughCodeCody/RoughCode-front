import { useEffect, useState } from "react";

import { BottomHeader, FlexDiv, Title, Text } from "@/components/elements";
import { useUser } from "@/features/auth";

import { useStatCard } from "../api";
import { EmailVerification, Welcome } from "../components";

export const Profile = () => {
  // 유저네임을 확인하여 스탯카드 요청하기 위함
  const userQuery = useUser();
  const name = userQuery.data?.nickname || "";

  const [userName, setUserName] = useState("");
  useEffect(() => setUserName(name), [name]);

  const statCardQuery = useStatCard({ userName });

  return (
    <FlexDiv direction="column" gap="2rem">
      <BottomHeader
        locations={["마이 페이지"]}
        menus={["프로필", "나의 게시물", "나의 리뷰", "즐겨찾기"]}
      />
      <FlexDiv direction="column" width="100%" gap="8rem">
        <FlexDiv direction="column" width="70%" gap="3rem">
          <Title
            title="프로필"
            description="나의 스탯 카드를 확인하고 이메일 알림을 설정할 수 있습니다."
          />
          <Welcome />
          {statCardQuery.data && (
            <FlexDiv direction="column" width="100%" gap="2rem">
              <img
                src={`data:image/svg+xml;utf8,${encodeURIComponent(
                  statCardQuery.data
                )}`}
                alt="stat-card"
                width="70%"
              />
              <FlexDiv direction="column" align="start" gap="1rem">
                <Text>{"Github 또는 Gitlab README.md 파일에서"}</Text>
                <Text color="main">
                  {
                    "!(개발새발 스탯카드!)[http://k8a306.p.ssafy.io:8080/api/v1/mypage?userName={당신의 깃허브 아이디}]"
                  }
                </Text>
                <Text>{"를 입력하여 활동 업적을 표현해보세요!"}</Text>
              </FlexDiv>
            </FlexDiv>
          )}
        </FlexDiv>
        <FlexDiv direction="column" width="70%" gap="3rem" align="start">
          <Title
            title="이메일 인증하기"
            description="이메일 계정을 연동하면 피드에 대한 실시간 알림을 받을 수 있습니다"
          />
          <EmailVerification />
        </FlexDiv>
      </FlexDiv>
    </FlexDiv>
  );
};
