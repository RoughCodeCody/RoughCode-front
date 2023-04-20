import styled, { css } from "styled-components";

const HomeContainer = styled.div`
  width: 100%;
  padding-top: 10rem;
  padding-bottom: 10rem;
  display: flex;
  flex-direction: column;
  align-items: center;
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

const TitleContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding-left: 3%;
`;

const Title = styled.p`
  font-weight: bold;
  font-size: 4rem;
`;

const Description = styled.p`
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--main-color);
`;

const NoticeContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1.3rem;
`;

export {
  HomeContainer,
  HomeContentsContainer,
  TitleContainer,
  Title,
  Description,
  NoticeContainer,
};
