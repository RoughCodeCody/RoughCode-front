import styled, { css } from "styled-components";

export const WhiteBoxNoshad = styled.div<{
  width?: string;
  height?: string;
  radius?: string;
  padding?: string;
}>`
  ${({ width, height, radius, padding }) => css`
    width: ${width || "100%"};
    height: ${height || "100%"};
    background-color: var(--white-color);
    border-radius: ${radius || "25px"};
    padding: ${padding || "1rem"};
  `}
`;
