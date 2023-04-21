import Link from "next/link";
import Image from "next/image";
import * as RadixNavMenu from "@radix-ui/react-navigation-menu";
import { Text } from "@/components/elements";
import { NoticeAlarmItem } from "@/components/notice-alarm-item";
import chevronDown from "@/assets/icons/chevron-down.svg";
import {
  RadixNavMenuRoot,
  RadixNavMenuItem,
  RadixNavMenuTrigger,
  RadixNavMenuContent,
  VersionLink,
  NoticeContent,
  NoticeTime,
} from "./style";

export const Notice = () => {
  //더미데이터
  const noticeContent = "프로젝트 및 코드 즐겨찾기 기능이 추가되었습니다!";
  const currentVersion = "V2";
  const currentVersionCreatedAt = "23.03.02 13:00";
  const versions = [
    { version: "V2", createdAt: "23.03.02 13:00" },
    { version: "V1", createdAt: "23.03.01 13:00" },
  ];

  return (
    <NoticeAlarmItem>
      <RadixNavMenuRoot>
        <RadixNavMenu.List>
          <RadixNavMenuItem>
            <RadixNavMenuTrigger>
              <Text color="main" size="1.5rem">
                {currentVersion}
              </Text>
              <Image src={chevronDown.src} alt="more" width={24} height={24} />
              {/* <img src="@/assets/icons/chevron-down.svg" alt="more" /> */}
            </RadixNavMenuTrigger>
            <RadixNavMenuContent>
              <ul>
                {versions.map(({ version, createdAt }, idx) => (
                  <VersionLink key={idx}>
                    <Link href="/">
                      <Text size="1.5rem" pointer={true}>
                        {version}
                      </Text>
                    </Link>
                    <NoticeTime>
                      <Text size="0.7rem">{createdAt.split(" ")[0]}</Text>
                      <Text size="0.7rem">{createdAt.split(" ")[1]}</Text>
                    </NoticeTime>
                  </VersionLink>
                ))}
              </ul>
            </RadixNavMenuContent>
          </RadixNavMenuItem>

          <RadixNavMenu.Indicator />
        </RadixNavMenu.List>

        <RadixNavMenu.Viewport />
      </RadixNavMenuRoot>

      <NoticeContent>
        <Text size="1.2rem" bold={true}>
          {noticeContent}
        </Text>
      </NoticeContent>
      <NoticeTime>
        <Text size="0.8rem">{currentVersionCreatedAt.split(" ")[0]}</Text>
        <Text size="0.8rem">{currentVersionCreatedAt.split(" ")[1]}</Text>
      </NoticeTime>
    </NoticeAlarmItem>
  );
};
