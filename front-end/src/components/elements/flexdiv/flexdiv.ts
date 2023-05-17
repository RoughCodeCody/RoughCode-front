import styled, { css } from "styled-components";

export const FlexDiv = styled.div<{
  position?: string;
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
  borderTop?: string;
  borderRight?: string;
  borderBottom?: string;
  borderLeft?: string;
  radius?: string;
  pointer?: boolean;
  overflow?: string;
  shadow?: boolean;
  top?: string;
  right?: string;
  bottom?: string;
  left?: string;
}>`
  ${({
    position,
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
    borderTop,
    borderRight,
    borderBottom,
    borderLeft,
    radius,
    pointer,
    overflow,
    shadow,
    top,
    right,
    bottom,
    left,
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
    position: ${position || "none"};
    padding: ${padding || "0"};
    padding-left: ${paddingX || "none"};
    padding-right: ${paddingX || "none"};
    padding-top: ${paddingY || "none"};
    padding-bottom: ${paddingY || "none"};
    margin: ${margin || "0"};
    background-color: ${color ? "var(--" + color + "-color)" : "none"};
    border-top: ${borderTop ? borderTop : "none"};
    border-right: ${borderRight ? borderRight : "none"};
    border-bottom: ${borderBottom ? borderBottom : "none"};
    border-left: ${borderLeft ? borderLeft : "none"};
    border: ${border ? border : "none"};
    border-radius: ${radius ? radius : "0"};
    cursor: ${pointer ? "pointer" : "auto"};
    overflow: ${overflow ? overflow : "none"};
    box-shadow: ${shadow ? "0px 4px 4px rgba(0, 0, 0, 0.05)" : "none"};
    top: ${top ? top : "none"};
    right: ${right ? right : "none"};
    bottom: ${bottom ? bottom : "none"};
    left: ${left ? left : "none"};
  `}
`;
