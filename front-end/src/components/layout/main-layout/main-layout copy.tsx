import Image from "next/image";
import Link from "next/link";
import * as React from "react";

import logo from "@/assets/dog-foot.png";
import { NEXT_PUBLIC_REDIRECT_URL } from "@/config";
import { FlexDiv, Text } from "@/components/elements";
import { useUser } from "@/features/auth";

import {
  LogoDiv,
  LogoText,
  FlexContainer,
  NavbarContainer,
  Main,
} from "./style";

const Logo = () => {
  return (
    <Link href="/">
      <LogoDiv>
        <Image src={logo} alt="Logo image" width={32} height={32} />
        <LogoText>개발새발</LogoText>
      </LogoDiv>
    </Link>
  );
};

const Menu = () => {
  return (
    <FlexDiv justify="center" align="center" gap="5rem">
      <Link href="/project">
        <Text bold={true} pointer={true}>
          프로젝트
        </Text>
      </Link>
      <Link href="/code-review">
        <Text bold={true} pointer={true}>
          코드 리뷰
        </Text>
      </Link>
    </FlexDiv>
  );
};

const UserNavigation = () => {
  const userQuery = useUser();

  return (
    <FlexDiv justify="center" align="center">
      {userQuery.isLoading && "Loading..."}
      {userQuery.data && userQuery.data.nickname.length === 0 && (
        <Link
          href={`${NEXT_PUBLIC_REDIRECT_URL}/oauth2/authorization/github?redirect_uri=${window.location.href}`}
        >
          <Text bold={true} pointer={true}>
            로그인
          </Text>
        </Link>
      )}
      {userQuery.data && userQuery.data.nickname.length !== 0 && (
        <Link href="/mypage/profile">
          <Text bold={true} pointer={true}>
            마이 페이지
          </Text>
        </Link>
      )}
    </FlexDiv>
  );
};

type MainLayoutProps = {
  children: React.ReactElement;
};

export const MainLayout = ({ children }: MainLayoutProps) => {
  return (
    <FlexContainer>
      <NavbarContainer>
        <Logo />
        <Menu />
        <UserNavigation />
      </NavbarContainer>
      <Main>{children}</Main>
    </FlexContainer>
  );
};
