import styled, { css } from "styled-components";

export const Text = styled.p<{
  size?: string;
  color?: string;
  bold?: boolean;
  pointer?: boolean;
  lineHeight?: string;
}>`
  ${({ size, color, bold, pointer, lineHeight }) => css`
    font-size: ${size || "1rem"};
    font-weight: ${bold ? "700" : "400"};
    color: ${color ? "var(--" + color + "-color)" : "var(--font-color)"};
    cursor: ${pointer ? "pointer" : "auto"};
    line-height: ${lineHeight ? lineHeight : "normal"};
  `}
`;
