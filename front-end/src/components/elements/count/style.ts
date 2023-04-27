import styled from "styled-components";

const IconWrapper = styled.div<{ color: string }>`
  color: ${({ color }) => `var(--${color}-color)`};
  font-size: 1.5rem;
`;

export { IconWrapper };
