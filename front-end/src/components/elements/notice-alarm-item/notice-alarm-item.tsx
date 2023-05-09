import { ReactNode } from "react";
import { RiCloseCircleLine } from "react-icons/ri";

import {
  NoticeAlarmWrapper,
  NoticeAlarmDiv,
  NoticeAlarmRibbon,
  DeleteIconContainer,
} from "./style";
import { useDeleteNotification } from "@/features/notifications/api";

interface Props {
  children: ReactNode;
  height?: string;
  width?: string;
  isDirectionRight?: boolean;
  color?: string;
  shadow?: boolean;
  isNotice?: boolean;
  alarmId?: string;
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
  alarmId,
}: Props) => {
  const deleteNotificationQuery = useDeleteNotification();

  return (
    <NoticeAlarmWrapper width={width} height={height} shadow={shadow}>
      <NoticeAlarmRibbon isDirectionRight={isDirectionRight} />
      <NoticeAlarmDiv
        isNotice={isNotice}
        isDirectionRight={isDirectionRight}
        color={color}
      >
        {children}
        {isNotice && alarmId ? (
          <DeleteIconContainer>
            <RiCloseCircleLine
              fontSize="2rem"
              color="var(--font-color)"
              onClick={() => {
                deleteNotificationQuery.mutate(alarmId);
              }}
            />
          </DeleteIconContainer>
        ) : (
          <></>
        )}
      </NoticeAlarmDiv>
    </NoticeAlarmWrapper>
  );
};
