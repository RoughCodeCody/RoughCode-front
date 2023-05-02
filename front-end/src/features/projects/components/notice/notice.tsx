import dayjs from "dayjs";

import { NoticeAlarmItem, Text } from "@/components/elements";

import { Version } from "../../types";
import { NoticeContent, NoticeTimeWrapper } from "./style";
import { VersionDropdown } from "./version-dropdown";

type NoticeProps = {
  notice: string;
  version: number;
  date: Date;
  versions: Version[];
};

export const Notice = ({ notice, version, date, versions }: NoticeProps) => {
  return (
    <NoticeAlarmItem>
      <VersionDropdown currentVersion={version} versions={versions} />

      <NoticeContent>
        <Text size="1.2rem" bold={true}>
          {notice}
        </Text>
      </NoticeContent>

      <NoticeTimeWrapper>
        <Text size="0.8rem">{dayjs(date).format("YY.MM.DD")}</Text>
        <Text size="0.8rem">{dayjs(date).format("HH:MM")}</Text>
      </NoticeTimeWrapper>
    </NoticeAlarmItem>
  );
};
