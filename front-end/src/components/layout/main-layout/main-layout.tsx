import Image from "next/image";
import Link from "next/link";
import { useRouter } from "next/router";
import * as React from "react";
import { useState } from "react";

import dog from "@/assets/dog.png";
import duck from "@/assets/duck.png";
import friends from "@/assets/dog-duck.png";
import { REDIRECT_URL } from "@/config";
import { FlexDiv, Text } from "@/components/elements";
import { useUser } from "@/features/auth";

import {
  LogoDiv,
  LogoText,
  FlexContainer,
  NavbarContainer,
  Main,
  Footer,
} from "./style";

const Logo = () => {
  return (
    <Link href="/">
      <LogoDiv>
        <Image src={dog} alt="Logo image" width={32} height={32} />
        <LogoText>개발새발</LogoText>
        <Image src={duck} alt="Logo image" width={32} height={32} />
      </LogoDiv>
    </Link>
  );
};

const Menu = () => {
  const router = useRouter();
  const isProject = router.asPath.includes("project") ? true : false;
  const isCode = router.asPath.includes("code") ? true : false;
  const [isHoveredProject, setIsHoveredProject] = useState(false);
  const [isHoveredCode, setIsHoveredCode] = useState(false);

  const handleMouseEnterProject = () => {
    setIsHoveredProject(true);
  };
  const handleMouseLeaveProject = () => {
    setIsHoveredProject(false);
  };
  const handleMouseEnterCode = () => {
    setIsHoveredCode(true);
  };
  const handleMouseLeaveCode = () => {
    setIsHoveredCode(false);
  };

  return (
    <FlexDiv justify="center" align="center" gap="5rem">
      <Link href="/project">
        <Text
          size="1.3rem"
          bold={true}
          pointer={true}
          color={isHoveredProject || isProject ? "main" : "font"}
          onMouseEnter={handleMouseEnterProject}
          onMouseLeave={handleMouseLeaveProject}
        >
          프로젝트
        </Text>
      </Link>
      <Link href="/code-review">
        <Text
          size="1.3rem"
          bold={true}
          pointer={true}
          color={isHoveredCode || isCode ? "main" : "font"}
          onMouseEnter={handleMouseEnterCode}
          onMouseLeave={handleMouseLeaveCode}
        >
          코드 리뷰
        </Text>
      </Link>
    </FlexDiv>
  );
};

const UserNavigation = () => {
  const userQuery = useUser();
  const router = useRouter();
  const isMypage = router.asPath.includes("mypage") ? true : false;
  const [isHoveredLogin, setIsHoveredLogin] = useState(false);
  const [isHoveredMypage, setIsHoveredMypage] = useState(false);

  const handleMouseEnterLogin = () => {
    setIsHoveredLogin(true);
  };
  const handleMouseLeaveLogin = () => {
    setIsHoveredLogin(false);
  };
  const handleMouseEnterMypage = () => {
    setIsHoveredMypage(true);
  };
  const handleMouseLeaveMypage = () => {
    setIsHoveredMypage(false);
  };

  return (
    <FlexDiv justify="center" align="center">
      {userQuery.isLoading && "Loading..."}
      {userQuery.data && userQuery.data.nickname.length === 0 && (
        <Link
          href={`${REDIRECT_URL}/oauth2/authorization/github?redirect_uri=${window.location.href}`}
        >
          <Text
            size="1.3rem"
            bold={true}
            pointer={true}
            color={isHoveredLogin ? "main" : "font"}
            onMouseEnter={handleMouseEnterLogin}
            onMouseLeave={handleMouseLeaveLogin}
          >
            로그인
          </Text>
        </Link>
      )}
      {userQuery.data && userQuery.data.nickname.length !== 0 && (
        <Link href="/mypage/profile">
          <Text
            size="1.3rem"
            bold={true}
            pointer={true}
            color={isHoveredMypage || isMypage ? "main" : "font"}
            onMouseEnter={handleMouseEnterMypage}
            onMouseLeave={handleMouseLeaveMypage}
          >
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
      <Footer>
        <Text bold={true}>개발새발</Text>
        <Text>Copyright &copy; 2023 team Cody.</Text>
        <FlexDiv position="absolute" right="3rem" bottom="calc(7rem - 4px)">
          <Image src={friends} alt="mascot" width={100} height={60} />
        </FlexDiv>
      </Footer>
    </FlexContainer>
  );
};
