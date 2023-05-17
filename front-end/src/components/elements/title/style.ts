import styled, { css } from "styled-components";

const TitleContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding-left: 3%;
`;

const TitleText = styled.p<{
  color?: string;
}>`
  ${({ color }) => css`
    margin: 0;
    font-weight: bold;
    font-size: 3rem;
    color: ${color ? "var(--" + color + "-color)" : "black"};
  `}
`;

const Description = styled.p<{
  color?: string;
}>`
  ${({ color }) => css`
    margin: 0;
    font-size: 1.2rem;
    font-weight: 600;
    color: ${color ? "var(--" + color + "-color)" : "var(--main-color)"};
  `}
`;

export { TitleContainer, TitleText, Description };
