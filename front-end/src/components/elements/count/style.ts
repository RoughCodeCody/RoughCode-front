import styled from "styled-components";

const IconWrapper = styled.div<{ color: string; pointer: boolean }>`
  color: ${({ color }) => `var(--${color}-color)`};
  font-size: 1.5rem;
  cursor: ${({ pointer }) => (pointer ? "pointer" : "auto")};
`;

export { IconWrapper };
