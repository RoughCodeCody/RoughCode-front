import Image from "next/image";
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

import { Title, Text } from "@/components/elements";
// import dogFoot from "@/assets/icons/dog-foot.png";
import dogFoot from "@/assets/dog-foot.png";
import dog from "@/assets/dog.png";
import duck from "@/assets/duck.png";
import friends from "@/assets/dog-duck.png";

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
            <Text size={"3rem"} bold={true} color={"banner"} lineHeight="4rem">
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
              content={"프로젝트를 공유하고 \n 피드백을 받아 반영해보세요"}
              imageUrl={dog}
              endPoint={"project"}
            ></HomeLinkCard>
            <HomeLinkCard
              title={"코드"}
              content={
                "코드를 공유하고 코드 리뷰를 받아보세요 \n 의견도 자유롭게 남길 수 있어요"
              }
              imageUrl={duck}
              endPoint={"code-review"}
            ></HomeLinkCard>
            <HomeLinkCard
              title={"로그인"}
              content={"깃허브 로그인을 하고 \n 나의 활동 업적을 쌓아보세요"}
              imageUrl={friends}
              endPoint={"login"}
            ></HomeLinkCard>
          </LinkCardwrapper>
        </LinkCardContainer>
      </UnauthHomeContainer>
    </>
  );
};
