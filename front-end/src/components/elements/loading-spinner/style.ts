import styled from "styled-components";

const LoadingSpinnerBackdrop = styled.div`
  position: fixed;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.3);
  z-index: 1100;
`;

const LoadingSpinnerWrapper = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1200;
`;

export { LoadingSpinnerBackdrop, LoadingSpinnerWrapper };
