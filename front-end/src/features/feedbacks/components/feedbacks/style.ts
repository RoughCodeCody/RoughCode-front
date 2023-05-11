import styled, { css } from "styled-components";

const FeedbackItemWrapper = styled.div<{ bgColor?: string; isMine: boolean }>`
  ${({ bgColor, isMine }) => css`
    width: 100%;
    padding: 1.2rem;
    box-shadow: ${isMine
      ? "0 0 1rem -0.3rem var(--orange-color)"
      : "4px 4px 4px var(--shad-color)"};
    background-color: ${bgColor
      ? "var(--" + bgColor + "-color)"
      : "var(--white-color)"};
    ${({ theme }) => theme.MIXINS.flexBox("column")}
  `}
`;

const FeedbackModifyInput = styled.input`
  width: 80%;
  height: 100%;
  border: none;
  outline: none;
`;

export { FeedbackItemWrapper, FeedbackModifyInput };
