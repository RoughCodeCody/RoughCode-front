import styled from "styled-components";
import { RiCloseFill } from "react-icons/ri";

const Backdrop = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.3);
  z-index: 500;
`;

const ModalWrapper = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 700;
  width: 40%;
`;

const StyledModal = styled.div`
  z-index: 100;
  background: var(--white-color);
  position: relative;
  margin: auto;
  border-radius: 15px;
  padding: 1rem;
`;

const Header = styled.div`
  border-radius: 15px 15px 0 0;
  display: flex;
  justify-content: space-between;
`;

const CloseButton = styled(RiCloseFill)`
  font-size: 1.5rem;
  color: var(--font-color);
  cursor: pointer;
`;

const ModalContent = styled.div`
  max-height: 30rem;
  padding: 0.5rem 0;
  /* overflow-x: hidden; */
  overflow-y: auto;

  -ms-overflow-style: none;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

export {
  Backdrop,
  ModalWrapper,
  StyledModal,
  Header,
  CloseButton,
  ModalContent,
};
