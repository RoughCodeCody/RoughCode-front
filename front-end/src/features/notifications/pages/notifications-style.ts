import styled, { css } from "styled-components";
import footPrintPic from "@/assets/foot-print.png";

const HomeContainer = styled.div`
  width: 100%;
  padding-top: 10rem;
  padding-bottom: 10rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-image: url(${footPrintPic.src});
  background-repeat: repeat;
`;

const HomeContentsContainer = styled.div`
  width: 100%;
  height: 100%;
  max-width: 1024px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3rem;
`;

const NoticeContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1.3rem;
`;

export { HomeContainer, HomeContentsContainer, NoticeContainer };
