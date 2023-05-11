import styled, { css } from "styled-components";

const ProjectInfoWrapper = styled.div``;

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

export { ProjectInfoWrapper, UrlApkBtn };
