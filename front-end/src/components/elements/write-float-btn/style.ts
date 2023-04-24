import styled from "styled-components";

const WriteFloatBtnWrapper = styled.div<{ color: string }>`
  color: ${({ color }) => `var(--${color}-color)`};
  font-size: 4rem;
  position: fixed;
  bottom: 4rem;
  right: 4rem;
  cursor: pointer;
`;

export { WriteFloatBtnWrapper };
