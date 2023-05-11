import React, {
  FunctionComponent,
  Dispatch,
  SetStateAction,
  ReactNode,
} from "react";
import ReactDOM from "react-dom";

import { Text } from "@/components/elements";

import {
  Backdrop,
  ModalWrapper,
  Header,
  StyledModal,
  CloseButton,
  ModalContent,
} from "./style";

export interface ModalProps {
  isOpen: boolean;
  setIsOpen: Dispatch<SetStateAction<boolean>>;
  modalContent: ReactNode;
  headerText: string;
  width?: string;
}

export const Modal: FunctionComponent<ModalProps> = ({
  isOpen,
  setIsOpen,
  modalContent,
  headerText,
  width,
}) => {
  const modal = (
    <>
      <Backdrop />
      <ModalWrapper width={width || "40%"}>
        <StyledModal>
          <Header>
            <Text color="main" bold={true}>
              {headerText}
            </Text>
            <CloseButton onClick={() => setIsOpen(false)}>X</CloseButton>
          </Header>
          <ModalContent>{modalContent}</ModalContent>
        </StyledModal>
      </ModalWrapper>
    </>
  );

  return isOpen ? ReactDOM.createPortal(modal, document.body) : null;
};
