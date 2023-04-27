import styled, { css } from "styled-components";

export const PortfolioCardGrid = styled.div`
  width: 100%;
  max-width: 1440px;
  display: grid;
  grid-template-columns: repeat(
    3,
    1fr
  ); // 3개의 열을 만듭니다. 각 열의 너비는 같습니다.
  gap: 3rem; // 그리드 셀 사이의 간격을 지정합니다. 필요에 따라 조정할 수 있습니다.
`;
