import { ReactNode } from "react";
import { NoticeAlarmWrapper, NoticeAlarmDiv, NoticeAlarmRibbon } from "./style";
import { DeleteIconCircle } from "../icon/delete-icon-circle";

interface Props {
  children: ReactNode;
  height?: string;
  width?: string;
  isDirectionRight?: boolean;
  color?: string;
  shadow?: boolean;
}

// 정훈
// children, width, height, isDirectionRight, shadow를 NoticeAlarmItem에 한번에 prop 주려고 수정
export const NoticeAlarmItem = ({
  children,
  width,
  height,
  isDirectionRight,
  color,
  shadow,
}: Props) => {
  return (
    <NoticeAlarmWrapper width={width} height={height} shadow={shadow}>
      <NoticeAlarmRibbon isDirectionRight={isDirectionRight} />
      <NoticeAlarmDiv
        isNotice={false}
        isDirectionRight={isDirectionRight}
        color={color}
      >
        {children}
      </NoticeAlarmDiv>
      <DeleteIconCircle />
    </NoticeAlarmWrapper>
  );
};
