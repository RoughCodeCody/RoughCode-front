import dayjs from "dayjs";
import { useRouter } from "next/router";
import { useState } from "react";

import { DropdownArrow, FlexDiv, Text } from "@/components/elements";

import { ProjectVersion } from "../../types";
import {
  VersionDropdownWrapper,
  DropdownContentWrapper,
  DropdownContent,
  DropdownItem,
  NoticeTimeWrapper,
  NoticeTime,
} from "./style";

type VersionDropdownProps = {
  currentVersion: number;
  versions: ProjectVersion[];
};

export const VersionDropdown = ({
  currentVersion,
  versions,
}: VersionDropdownProps) => {
  const router = useRouter();
  const [versionsOpen, setVersionsOpen] = useState(false);

  return (
    <VersionDropdownWrapper>
      <FlexDiv gap="1.5rem">
        <Text color="main" size="1.5rem">
          {`V${currentVersion}`}
        </Text>
        <DropdownArrow
          onClick={() => setVersionsOpen((prev) => !prev)}
          isopen={versionsOpen.toString()}
          size={22}
        />
      </FlexDiv>

      {versionsOpen && (
        <DropdownContentWrapper>
          <DropdownContent data-isOpen={versionsOpen}>
            {versions.map(({ version, date, projectId }) => (
              <DropdownItem
                key={projectId}
                onClick={() => {
                  if (version !== currentVersion) {
                    router.push(`/project/${projectId}`);
                    setVersionsOpen(false);
                  }
                }}
              >
                <Text size="1.5rem" pointer={true}>
                  {`V${version}`}
                </Text>
                <NoticeTimeWrapper>
                  <NoticeTime>{dayjs(date).format("YY.MM.DD")}</NoticeTime>
                  <NoticeTime>{dayjs(date).format("HH:MM")}</NoticeTime>
                </NoticeTimeWrapper>
              </DropdownItem>
            ))}
          </DropdownContent>
        </DropdownContentWrapper>
      )}
    </VersionDropdownWrapper>
  );
};
