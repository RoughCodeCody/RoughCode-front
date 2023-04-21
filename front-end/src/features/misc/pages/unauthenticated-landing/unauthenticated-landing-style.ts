import styled, { css } from "styled-components";
import banner from "@/assets/banner.jpg";

const UnauthHomeContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Banner = styled.div`
  width: 94vw;
  max-width: 1440px;
  height: 33vw;
  max-height: 495px;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1rem;

  padding: 0 14rem 4rem 14rem;
  box-shadow: 40px 40px 100px rgba(0, 0, 0, 0.15);
  border-radius: 0px 0px 48px 48px;
  overflow: hidden;
  background-image: url(${banner.src});
  background-size: cover;
`;

const BannerTitleContainer = styled.div`
  z-index: 1;
  width: fit-content;
  padding: 0.3rem 0 0.3rem 0;
  border-top: solid 2px var(--banner-color);
  border-bottom: solid 2px var(--banner-color);
  opacity: 0.7;
`;
const BannerContentContainer = styled.div`
  z-index: 1;
  width: fit-content;
  padding: 0.3rem 0 0.3rem 0;
`;

const FootContainer = styled.div`
  position: absolute;
  top: 10%;
  right: -5%;
  transform: rotate(-70deg);
`;

const LinkCardWrapper = styled.div`
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  padding: 0 5% 0 5%;
  gap: 10px;
  width: 100%;
  height: 40vw;
  min-height: 748px;
  max-height: 950px;
  margin-top: 6rem;
  background: linear-gradient(90deg, #319795 0%, rgba(49, 151, 149, 0.45) 100%);
  border-radius: 64px 0px 0px 192px;
`;
export {
  UnauthHomeContainer,
  Banner,
  LinkCardWrapper,
  BannerTitleContainer,
  BannerContentContainer,
  FootContainer,
};
