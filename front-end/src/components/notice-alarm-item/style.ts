import styled, { css } from "styled-components";
import { RiCloseCircleLine } from "react-icons/ri";

const NoticeAlarmWrapper = styled.div<{
  width?: string;
  height?: string;
  shadow?: boolean;
}>`
  ${({ width, height, shadow }) => css`
    width: ${width || "90vw"};
    height: ${height || "6rem"};
    margin: auto;
    position: relative;
    filter: ${shadow ? "drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.1))" : "none"};
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

// 정훈
// wrapper의 height에 ribbon이 반응하도록 수정
const NoticeAlarmRibbon = styled.div<{
  isDirectionRight?: boolean;
  height?: string;
}>`
  width: 0.5rem;
  height: ${({ height }) => (height ? height : "6rem")};
  position: absolute;
  left: ${({ isDirectionRight }) =>
    isDirectionRight ? "calc(100% - 0.5rem)" : "0"};
  background-color: var(--main-color);
`;

const DeleteIconContainer = styled.div`
  position: absolute;
  right: 4rem;
  top: 50%;
  transform: translate(-50%, -50%);
  cursor: pointer;
`;
export {
  NoticeAlarmWrapper,
  NoticeAlarmDiv,
  NoticeAlarmRibbon,
  DeleteIconContainer,
};
