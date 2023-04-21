import styled, { css } from "styled-components";

const TitleContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding-left: 3%;
`;

const TitleText = styled.p`
  font-weight: bold;
  font-size: 4rem;
`;

const Description = styled.p`
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--main-color);
`;

export { TitleContainer, TitleText, Description };
