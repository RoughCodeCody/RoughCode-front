import Image from "next/image";
import { Title } from "@/components/title";
import {
  UnauthHomeContainer,
  Banner,
  LinkCardContainer,
  BannerTitleContainer,
  BannerContentContainer,
  FootContainer,
  LinkCardBackground,
  LinkCardwrapper,
} from "./unauthenticated-landing-style";
import { HomeLinkCard } from "./components/home-link-card";
import { Text } from "@/components/elements";

// import dogFoot from "@/assets/icons/dog-foot.png";
import dogFoot from "@/assets/dog-foot.png";

export const UnauthenticatedLanding = () => {
  return (
    <>
      <UnauthHomeContainer>
        <Banner>
          <BannerTitleContainer>
            <Text size={"1.5rem"} bold={true} color={"banner"}>
              내가 짠 개발새발인 코드 내 눈엔 가장 예쁘다
            </Text>
          </BannerTitleContainer>
          <BannerContentContainer>
            <Text size={"3rem"} bold={true} color={"banner"}>
              잠든 토이 프로젝트를 깨우고
              <br />
              엉킨 코드를 풀어보세요
            </Text>
          </BannerContentContainer>

          <FootContainer>
            <Image src={dogFoot} width={500} height={500} alt={"foot"} />
          </FootContainer>
        </Banner>

        <LinkCardContainer>
          <LinkCardBackground />
          <Title
            title="시작하기"
            description="프로젝트와 코드를 만나보세요"
            color="bg"
          ></Title>
          <LinkCardwrapper>
            <HomeLinkCard
              title={"프로젝트"}
              content={
                "우리의 토이 프로젝트를 공유합니다 우리의 토이 프로젝트를 공유합니다"
              }
              imageUrl={"Project URL"}
            ></HomeLinkCard>
            <HomeLinkCard
              title={"코드"}
              content={
                "우리의 토이 코드를 공유합니다 우리의 토이 코드를 공유합니다"
              }
              imageUrl={"code URL"}
            ></HomeLinkCard>
            <HomeLinkCard
              title={"로그인"}
              content={"로그인로그인로그인 로그인로그인로그인"}
              imageUrl={"login URL"}
            ></HomeLinkCard>
          </LinkCardwrapper>
        </LinkCardContainer>
      </UnauthHomeContainer>
    </>
  );
};
