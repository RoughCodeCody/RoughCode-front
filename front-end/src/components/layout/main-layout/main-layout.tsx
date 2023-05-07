import Image from "next/image";
import Link from "next/link";
import * as React from "react";

import logo from "@/assets/dog-foot.png";
import { API_URL } from "@/config";
import { FlexDiv } from "@/components/elements";
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
    <FlexDiv direction="row" justify="center" align="center" gap="5rem">
      <Link href="/project">프로젝트</Link>
      <Link href="/code-review">코드 리뷰</Link>
    </FlexDiv>
  );
};

const UserNavigation = () => {
  const userQuery = useUser();

  return (
    <FlexDiv direction="row" justify="center" align="center">
      {userQuery.isLoading && "Loading..."}
      {userQuery.data && userQuery.data.nickname.length === 0 && (
        <Link
          href={`${API_URL}/oauth2/authorization/github?redirect_uri=${window.location.href}`}
        >
          로그인
        </Link>
      )}
      {userQuery.data && <Link href="/profile">마이 페이지</Link>}
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
