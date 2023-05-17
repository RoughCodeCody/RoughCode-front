import styled from "styled-components";
import { WhiteBoxShad } from "../white-box-shad";

const BackToTopWrapper = styled(WhiteBoxShad)<{ show: boolean }>`
  width: 9rem;
  height: 2.5rem;
  position: fixed;
  right: 4rem;
  bottom: 2rem;
  cursor: pointer;
  z-index: 1000;
  opacity: ${({ show }) => (show ? "1" : "0")};
  transition: all 0.3s ease;
  ${({ theme }) => theme.MIXINS.flexBox()}
`;

export { BackToTopWrapper };
