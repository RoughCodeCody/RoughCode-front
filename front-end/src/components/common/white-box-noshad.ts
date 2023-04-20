import styled, { css } from "styled-components";

export const WhiteBoxNoshad = styled.div<{
  width?: string;
  height?: string;
  radius?: string;
}>`
  ${({ width, height, radius }) => css`
    width: ${width || "100%"};
    height: ${height || "100%"};
    background-color: var(--white-color);
    border-radius: ${radius || "25px"};
  `}
`;
