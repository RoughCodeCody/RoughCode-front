import styled, { css } from "styled-components";

// export const WhiteBoxShad = styled.div<{
//   width?: string;
//   height?: string;
//   radius?: string;
//   shadColor?: string;
//   padding?: string;
//   margin?: string;
// }>`
//   ${({ width, height, radius, shadColor, padding, margin }) => css`
//     width: ${width || "100%"};
//     height: ${height || "100%"};
//     background-color: var(--white-color);
//     border-radius: ${radius || "25px"};
//     box-shadow: 0 0 1.5rem -0.5rem var(--${shadColor || "shad"}-color);
//     padding: ${padding || "0"};
//     margin: ${margin || "0"};
//   `}
// `;
export const WhiteBoxShad = styled.div<{
  width?: string;
  height?: string;
  radius?: string;
  shadColor?: string;
  padding?: string;
  margin?: string;
}>`
  ${({ width, height, radius, shadColor, padding, margin }) => css`
    width: ${width || "100%"};
    height: ${height || "100%"};
    background-color: var(--white-color);
    border-radius: ${radius || "25px"};
    box-shadow: 0 0 1.5rem -0.5rem var(--${shadColor || "shad"}-color);
    padding: ${padding || "0"};
    margin: ${margin || "0"};
    border: 1px solid transparent;
  `}

  &:hover {
    border: 1px solid var(--main-color);
  }
`;
