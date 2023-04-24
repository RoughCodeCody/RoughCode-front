import styled, { css } from "styled-components";

export const FlexDiv = styled.div<{
  width?: string;
  minWidth?: string;
  maxWidth?: string;
  height?: string;
  minHeight?: string;
  maxHeight?: string;
  justify?: string;
  align?: string;
  direction?: string;
  wrap?: string;
  gap?: string;
  padding?: string;
  paddingX?: string;
  paddingY?: string;
  margin?: string;
  color?: string;
  border?: string;
  radius?: string;
  pointer?: boolean;
  overflow?: string;
}>`
  ${({
    width,
    minWidth,
    maxWidth,
    height,
    minHeight,
    maxHeight,
    justify,
    align,
    direction,
    wrap,
    gap,
    padding,
    paddingX,
    paddingY,
    margin,
    color,
    border,
    radius,
    pointer,
    overflow,
  }) => css`
    display: flex;
    flex-direction: ${direction || "row"};
    justify-content: ${justify || "center"};
    align-items: ${align || "center"};
    flex-wrap: ${wrap || "nowrap"};
    gap: ${gap || "0"};
    width: ${width || "auto"};
    min-width: ${minWidth || "none"};
    max-width: ${maxWidth || "none"};
    height: ${height || "auto"};
    min-height: ${minHeight || "none"};
    max-height: ${maxHeight || "none"};
    padding: ${padding || "0"};
    padding-left: ${paddingX || "none"};
    padding-right: ${paddingX || "none"};
    padding-top: ${paddingY || "none"};
    padding-bottom: ${paddingY || "none"};
    margin: ${margin || "0"};
    background-color: ${color ? "var(--" + color + "-color)" : "none"};
    border: ${border ? border : "none"};
    border-radius: ${radius ? radius : "0"};
    cursor: ${pointer ? "pointer" : "auto"};
    overflow: ${overflow ? overflow : "none"};
  `}
`;
