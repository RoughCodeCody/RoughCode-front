import styled, { css } from "styled-components";

const BtnWrapper = styled.button<{
  bgColor?: string;
  color?: string;
  padding?: string;
  margin?: string;
  fontSize?: string;
}>`
  ${({ bgColor, color, padding, margin, fontSize }) => css`
    background-color: ${bgColor
      ? "var(--" + bgColor + "-color)"
      : "var(--main-color)"};
    color: ${color ? "var(--" + color + "-color)" : "var(--white-color)"};
    border-radius: 5px;
    padding: ${padding || "0.5rem"};
    margin: ${margin || "0"};
    font-size: ${fontSize || "1rem"};
    cursor: pointer;
  `}
`;

export { BtnWrapper };
