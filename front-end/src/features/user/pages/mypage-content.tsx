import { useState } from "react";

import { FlexDiv, Text } from "@/components/elements";
import { TabDiv } from "../components/common-layout/style";

import {
  CommonLayout,
  ContentsWrapper,
  ProjectContainer,
  CodeContainer,
} from "../components";

type MyPageContentProps = {
  title: string;
  description: string;
  endPoint: "" | "feedback" | "favorite";
};

export const MyPageContent = ({
  title,
  description,
  endPoint,
}: MyPageContentProps) => {
  const [isProject, setIsProject] = useState(true);

  return (
    <CommonLayout title={title} description={description}>
      <TabDiv
        width="100%"
        paddingX="4rem"
        justify="start"
        borderBottom="solid 2px var(--main-color)"
      >
        <TabDiv
          position="relative"
          top={isProject ? "2px" : "1px"}
          radius="20px 20px 0 0"
          padding="0.7rem 2rem 0.3rem 2rem"
          border={isProject ? "solid 2px var(--main-color)" : "none"}
          borderBottom="solid 2px var(--bg-color)"
          pointer={true}
          onClick={() => {
            if (!isProject) {
              setIsProject(true);
            }
          }}
        >
          <Text
            pointer={true}
            size="1.5rem"
            bold={true}
            color={isProject ? "main" : "font"}
          >
            프로젝트
          </Text>
        </TabDiv>
        <TabDiv
          position="relative"
          top={!isProject ? "2px" : "1px"}
          radius="20px 20px 0 0"
          padding="0.7rem 2rem 0.3rem 2rem"
          border={!isProject ? "solid 2px var(--main-color)" : "none"}
          borderBottom="solid 2px var(--bg-color)"
          pointer={true}
          onClick={() => {
            if (isProject) {
              setIsProject(false);
            }
          }}
        >
          <Text
            pointer={true}
            size="1.5rem"
            bold={true}
            color={isProject ? "font" : "main"}
          >
            코드
          </Text>
        </TabDiv>
      </TabDiv>
      {isProject ? (
        <ProjectContainer endPoint={endPoint} />
      ) : (
        <CodeContainer endPoint={endPoint} />
      )}
    </CommonLayout>
  );
};
