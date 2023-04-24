import Link from "next/link";
import styled from "styled-components";

const WriteFloatBtnWrapper = styled(Link)<{ color: string }>`
  color: ${({ color }) => `var(--${color}-color)`};
  font-size: 5rem;
  position: fixed;
  bottom: 5rem;
  right: 5rem;
  cursor: pointer;
`;

export { WriteFloatBtnWrapper };
