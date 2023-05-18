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

const FeedbackModifyTextarea = styled.textarea`
  width: 75%;
  height: 100%;
  resize: none;
  border: none;
  outline: none;
  font-family: "NEXON Lv2 Gothic", "Pretendard-Regular", Arial, Helvetica,
    sans-serif;
`;

export { FeedbackItemWrapper, FeedbackModifyTextarea };
