import styled, { css } from "styled-components";

export const FlexDiv = styled.div<{
  width?: string;
  height?: string;
  justify?: string;
  align?: string;
  direction?: string;
  wrap?: string;
  gap?: string;
  padding?: string;
  margin?: string;
}>`
  ${({
    width,
    height,
    justify,
    align,
    direction,
    wrap,
    gap,
    padding,
    margin,
  }) => css`
    display: flex;
    flex-direction: ${direction || "row"};
    justify-content: ${justify || "center"};
    align-items: ${align || "center"};
    flex-wrap: ${wrap || "nowrap"};
    gap: ${gap || "0"};
    width: ${width || "auto"};
    height: ${height || "auto"};
    padding: ${padding || "0"};
    margin: ${margin || "0"};
  `}
`;
