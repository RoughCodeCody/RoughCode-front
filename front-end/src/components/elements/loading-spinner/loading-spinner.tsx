import React, { FunctionComponent } from "react";
import ReactDOM from "react-dom";

import { Spinner } from "@/components/elements";

import { LoadingSpinnerBackdrop, LoadingSpinnerWrapper } from "./style";

export interface LoadingSpinnerProps {
  isOpen: boolean;
}

export const LoadingSpinner: FunctionComponent<LoadingSpinnerProps> = ({
  isOpen,
}) => {
  const loadingSpinner = (
    <>
      <LoadingSpinnerBackdrop />
      <LoadingSpinnerWrapper>
        <Spinner size={300} />
      </LoadingSpinnerWrapper>
    </>
  );

  return isOpen ? ReactDOM.createPortal(loadingSpinner, document.body) : null;
};
