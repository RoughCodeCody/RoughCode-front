import styled, { keyframes } from "styled-components";
import Image from "next/image";
import spinner from "@/assets/spinner-static-image.png";

const spinAnimation = keyframes`
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(-360deg);
  }
`;

const SpinnerWrapper = styled.div<{ size: number }>`
  width: ${(props) => props.size}px;
  height: ${(props) => props.size}px;
`;

const SpinnerImage = styled(Image)`
  animation: ${spinAnimation} 1.5s infinite linear;
`;

export const Spinner = ({ size }: { size: number }) => {
  return (
    <SpinnerWrapper size={size}>
      <SpinnerImage src={spinner} alt="spinner" width={size} height={size} />
    </SpinnerWrapper>
  );
};

export default Spinner;
