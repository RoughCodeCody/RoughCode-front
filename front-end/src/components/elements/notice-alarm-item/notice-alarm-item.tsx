import { ReactNode } from "react";
import { RiCloseCircleLine } from "react-icons/ri";
import {
  NoticeAlarmWrapper,
  NoticeAlarmDiv,
  NoticeAlarmRibbon,
  DeleteIconContainer,
} from "./style";

interface Props {
  children: ReactNode;
  height?: string;
  width?: string;
  isDirectionRight?: boolean;
  color?: string;
  shadow?: boolean;
  isNotice?: boolean;
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
  isNotice,
}: Props) => {
  return (
    <NoticeAlarmWrapper width={width} height={height} shadow={shadow}>
      <NoticeAlarmRibbon isDirectionRight={isDirectionRight} />
      <NoticeAlarmDiv
        isNotice={isNotice}
        isDirectionRight={isDirectionRight}
        color={color}
      >
        {children}
        <DeleteIconContainer>
          <RiCloseCircleLine fontSize="2rem" color="var(--font-color)" />
        </DeleteIconContainer>
      </NoticeAlarmDiv>
    </NoticeAlarmWrapper>
  );
};
