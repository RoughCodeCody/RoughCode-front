import styled, { css } from "styled-components";

const NoticeAlarmWrapper = styled.div<{
  width?: string;
  height?: string;
}>`
  ${({ width, height }) => css`
    width: ${width || "90vw"};
    height: ${height || "6rem"};
    margin: auto;
    position: relative;
  `}
`;

const NoticeAlarmDiv = styled.div<{
  color?: string;
  isNotice?: boolean;
  isDirectionRight?: boolean;
}>`
  ${({ color, isNotice, isDirectionRight }) => css`
    width: 100%;
    height: 100%;
    background-color: ${color
      ? "var(--" + color + "-color)"
      : "var(--sub-one-color)"};
    border: ${isNotice ? "1px solid var(--main-color)" : "none"};
    border-radius: ${isDirectionRight ? "3rem 0 0 3rem" : "0 3rem 3rem 0"};
    padding-left: ${isDirectionRight ? "0" : "0.5rem"};
    padding-right: ${isDirectionRight ? "0.5rem" : "0"};
    ${({ theme }) =>
      isNotice
        ? theme.MIXINS.flexBox("row", "space-between")
        : theme.MIXINS.flexBox()}
  `}
`;

const NoticeAlarmRibbon = styled.div<{ isDirectionRight?: boolean }>`
  width: 0.5rem;
  height: 6rem;
  position: absolute;
  left: ${({ isDirectionRight }) =>
    isDirectionRight ? "calc(100% - 0.5rem)" : "0"};
  background-color: var(--main-color);
`;

export { NoticeAlarmWrapper, NoticeAlarmDiv, NoticeAlarmRibbon };
