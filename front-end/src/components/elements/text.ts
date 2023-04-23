import styled, { css } from "styled-components";

export const Text = styled.p<{
  size?: string;
  color?: string;
  bold?: boolean;
  pointer?: boolean;
  padding?: string;
  margin?: string;
}>`
  ${({ size, color, bold, pointer, padding, margin }) => css`
    font-size: ${size || "1rem"};
    font-weight: ${bold ? "700" : "400"};
    color: ${color ? "var(--" + color + "-color)" : "var(--font-color)"};
    cursor: ${pointer ? "pointer" : "auto"};
    padding: ${padding || "0"};
    margin: ${margin || "0"};
  `}
`;