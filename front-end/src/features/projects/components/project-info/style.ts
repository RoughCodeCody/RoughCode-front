import styled, { css } from "styled-components";

const UrlApkBtn = styled.button<{ isClosed: boolean }>`
  ${({ isClosed }) => css`
    width: 100%;
    height: 5rem;
    background-color: ${isClosed
      ? "var(--sub-two-color)"
      : "var(--main-color)"};
    color: var(--sub-one-color);
    font-size: 2rem;
    font-weight: 700;
    margin-top: 0.3rem;
    cursor: ${isClosed ? "default" : "pointer"};

    &:hover {
      color: ${isClosed ? "var(--sub-one-color)" : "var(--white-color)"};
    }
  `}
`;

const UrlLoadingBackdrop = styled.div`
  position: fixed;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.3);
  z-index: 500;
`;

const UrlLoadingSpinnerWrapper = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 700;
`;

export { UrlApkBtn, UrlLoadingBackdrop, UrlLoadingSpinnerWrapper };
