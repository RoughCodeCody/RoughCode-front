import React, { FunctionComponent } from "react";
import ReactDOM from "react-dom";

import { Spinner } from "@/components/elements";

import { UrlLoadingBackdrop, UrlLoadingSpinnerWrapper } from "./style";

export interface UrlLoadingProps {
  isOpen: boolean;
}

export const UrlLoading: FunctionComponent<UrlLoadingProps> = ({ isOpen }) => {
  const urlLoading = (
    <>
      <UrlLoadingBackdrop />
      <UrlLoadingSpinnerWrapper>
        <Spinner size={300} />
      </UrlLoadingSpinnerWrapper>
    </>
  );

  return isOpen ? ReactDOM.createPortal(urlLoading, document.body) : null;
};
