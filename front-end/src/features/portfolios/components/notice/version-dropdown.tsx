import { useState } from "react";
import { DropdownArrow, FlexDiv, Text } from "@/components/elements";
import {
  VersionDropdownWrapper,
  DropdownContentWrapper,
  DropdownContent,
  DropdownItem,
  NoticeTimeWrapper,
  NoticeTime,
} from "./style";

type VersionDropdownProps = {
  currentVersion: string;
  versions: { version: string; createdAt: string }[];
};

export const VersionDropdown = ({
  currentVersion,
  versions,
}: VersionDropdownProps) => {
  const [versionsOpen, setVersionsOpen] = useState<boolean>(false);

  return (
    <VersionDropdownWrapper>
      <FlexDiv gap="1.5rem">
        <Text color="main" size="1.5rem">
          {currentVersion}
        </Text>
        <DropdownArrow
          onClick={() => setVersionsOpen((prev) => !prev)}
          isOpen={versionsOpen}
          size={22}
        />
      </FlexDiv>

      {versionsOpen && (
        <DropdownContentWrapper>
          <DropdownContent data-isOpen={versionsOpen}>
            {versions.map(({ version, createdAt }, idx) => (
              <DropdownItem key={idx}>
                <Text size="1.5rem" pointer={true}>
                  {version}
                </Text>
                <NoticeTimeWrapper>
                  <NoticeTime>{createdAt.split(" ")[0]}</NoticeTime>
                  <NoticeTime>{createdAt.split(" ")[1]}</NoticeTime>
                </NoticeTimeWrapper>
              </DropdownItem>
            ))}
          </DropdownContent>
        </DropdownContentWrapper>
      )}
    </VersionDropdownWrapper>
  );
};
