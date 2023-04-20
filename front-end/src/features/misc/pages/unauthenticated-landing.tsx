import {
  UnauthHomeContainer,
  Banner,
  LinkCardWrapper,
  LinkCard,
  LinkCardHeader,
  LinkCardContent,
  LinkCardImage,
} from "./unauthenticated-landing-style";

export const UnauthenticatedLanding = () => {
  return (
    <>
      <UnauthHomeContainer>
        <Banner></Banner>

        <LinkCardWrapper>
          <LinkCard>
            <LinkCardHeader></LinkCardHeader>
            <LinkCardContent></LinkCardContent>
            <LinkCardImage></LinkCardImage>
          </LinkCard>
          <LinkCard>
            <LinkCardHeader></LinkCardHeader>
            <LinkCardContent></LinkCardContent>
            <LinkCardImage></LinkCardImage>
          </LinkCard>
          <LinkCard>
            <LinkCardHeader></LinkCardHeader>
            <LinkCardContent></LinkCardContent>
            <LinkCardImage></LinkCardImage>
          </LinkCard>
        </LinkCardWrapper>
      </UnauthHomeContainer>
    </>
  );
};
