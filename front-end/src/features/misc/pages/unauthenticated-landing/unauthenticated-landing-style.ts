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
  background-color: green;
  box-shadow: 40px 40px 100px rgba(0, 0, 0, 0.15);
  border-radius: 0px 0px 48px 48px;
  background-image: url(${banner.src});
  background-size: cover;
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
  margin-top: 15rem;
  background: linear-gradient(90deg, #319795 0%, rgba(49, 151, 149, 0.45) 100%);
  border-radius: 64px 0px 0px 192px;
`;
export { UnauthHomeContainer, Banner, LinkCardWrapper };
