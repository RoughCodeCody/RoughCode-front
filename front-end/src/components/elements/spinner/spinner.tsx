import Image from "next/image";
import styled, { keyframes } from "styled-components";

import spinner from "@/assets/spinner.gif";

const spin = keyframes`
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
`;

const StyleCircle = styled.circle`
  opacity: ${(props) => props.opacity};
`;

const StyledSvg = styled.svg`
  color: rgb(37 99 235);
  width: 6rem;
  height: 6rem;
  animation: ${spin} 1s linear infinite;
`;

const StyledSpan = styled.span`
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border-width: 0;
`;

export const Spinner = ({ size }: { size: number }) => {
  return (
    <>
      <Image src={spinner} alt="spinner" width={size} height={size} />
      {/* <StyledSvg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        data-testid="loading"
      >
        <StyleCircle
          opacity="0.25"
          cx="12"
          cy="12"
          r="10"
          stroke="currentColor"
          strokeWidth="4"
        ></StyleCircle>
        <path
          opacity="0.75"
          fill="currentColor"
          d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
        ></path>
      </StyledSvg>
      <StyledSpan>Loading</StyledSpan> */}
    </>
  );
};
