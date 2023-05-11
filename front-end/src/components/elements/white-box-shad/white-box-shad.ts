import styled, { css } from "styled-components";

export const WhiteBoxShad = styled.div<{
  width?: string;
  height?: string;
  radius?: string;
  shadColor?: string;
  padding?: string;
}>`
  ${({ width, height, radius, shadColor, padding }) => css`
    width: ${width || "100%"};
    height: ${height || "100%"};
    background-color: var(--white-color);
    border-radius: ${radius || "25px"};
    box-shadow: 0 0 1.5rem -0.5rem var(--${shadColor || "shad"}-color);
    padding: ${padding || "0"};
  `}
`;
