import styled, { css } from "styled-components";

const BtnWrapper = styled.button<{
  bgColor?: string;
  color?: string;
  padding?: string;
  margin?: string;
  fontSize?: string;
  width?: string;
  height?: string;
  display?: string;
  justify?: string;
  align?: string;
  disabled?: boolean;
}>`
  ${({
    bgColor,
    color,
    padding,
    margin,
    fontSize,
    width,
    height,
    display,
    justify,
    align,
    disabled,
  }) => css`
    background-color: ${bgColor
      ? "var(--" + bgColor + "-color)"
      : "var(--main-color)"};
    color: ${color ? "var(--" + color + "-color)" : "var(--white-color)"};
    border-radius: 5px;
    padding: ${padding || "0.5rem"};
    margin: ${margin || "0"};
    font-size: ${fontSize || "1rem"};
    width: ${width || "none"};
    height: ${height || "none"};
    display: ${display || "inline"};
    justify-content: ${justify || "center"};
    align-items: ${align || "center"};
    cursor: pointer;
    ${disabled &&
    `
      cursor: default;
    `}
  `}
`;

export { BtnWrapper };
