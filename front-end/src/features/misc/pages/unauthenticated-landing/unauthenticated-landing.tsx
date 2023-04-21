import {
  UnauthHomeContainer,
  Banner,
  LinkCardWrapper,
} from "./unauthenticated-landing-style";
import { HomeLinkCard } from "@/components/card/home-link-card";

export const UnauthenticatedLanding = () => {
  return (
    <>
      <UnauthHomeContainer>
        <Banner />

        <LinkCardWrapper>
          <HomeLinkCard
            title={"프로젝트"}
            content={"우리의 토이 프로젝트를 공유합니다"}
            imageUrl={"Project URL"}
          ></HomeLinkCard>
          <HomeLinkCard
            title={"코드"}
            content={"우리의 토이 코드를 공유합니다"}
            imageUrl={"code URL"}
          ></HomeLinkCard>
          <HomeLinkCard
            title={"로그인"}
            content={"로그인로그인로그인"}
            imageUrl={"login URL"}
          ></HomeLinkCard>
        </LinkCardWrapper>
      </UnauthHomeContainer>
    </>
  );
};
