import { ReactNode } from "react";
import { NoticeAlarmWrapper, NoticeAlarmDiv, NoticeAlarmRibbon } from "./style";

interface Props {
  children: ReactNode;
}

export const NoticeAlarmItem = ({ children }: Props) => {
  return (
    <NoticeAlarmWrapper>
      <NoticeAlarmRibbon />
      <NoticeAlarmDiv isNotice={true}>{children}</NoticeAlarmDiv>
    </NoticeAlarmWrapper>
  );
};
