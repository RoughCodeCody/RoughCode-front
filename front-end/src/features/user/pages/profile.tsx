import { BottomHeader, FlexDiv, Title } from "@/components/elements";

import { StatCard3 } from "../components/stat-card";
import { Welcome } from "../components/welcome";
import { EmailVerification } from "../components";
import { useUser } from "@/features/auth";
import { useStatCard } from "../api/get-stat-card";
import { useEffect, useState } from "react";

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
            <img
              // src={statCardQuery.data}
              src={`data:image/svg+xml;utf8,${encodeURIComponent(
                statCardQuery.data
              )}`}
              // src={`data:image/svg+xml;utf8,${encodeURIComponent(StatCard3())}`}
              alt="stat-card"
              width="70%"
            />
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
