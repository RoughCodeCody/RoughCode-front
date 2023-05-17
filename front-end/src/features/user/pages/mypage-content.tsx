import { useState } from "react";

import { FlexDiv, Text } from "@/components/elements";

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
      <FlexDiv
        width="100%"
        paddingX="4rem"
        justify="start"
        borderBottom="solid 2px var(--main-color)"
      >
        <FlexDiv
          position="relative"
          top={isProject ? "2px" : "1px"}
          radius="20px 20px 0 0"
          padding="0.7rem 2rem 0.3rem 2rem"
          borderTop={isProject ? "solid 2px var(--main-color)" : "none"}
          borderLeft={isProject ? "solid 2px var(--main-color)" : "none"}
          borderRight={isProject ? "solid 2px var(--main-color)" : "none"}
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
        </FlexDiv>
        <FlexDiv
          position="relative"
          top={!isProject ? "2px" : "1px"}
          radius="20px 20px 0 0"
          padding="0.7rem 2rem 0.3rem 2rem"
          borderTop={!isProject ? "solid 2px var(--main-color)" : "none"}
          borderLeft={!isProject ? "solid 2px var(--main-color)" : "none"}
          borderRight={!isProject ? "solid 2px var(--main-color)" : "none"}
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
        </FlexDiv>
      </FlexDiv>
      {isProject ? (
        <ProjectContainer endPoint={endPoint} />
      ) : (
        <CodeContainer endPoint={endPoint} />
      )}

      {/* {isProject ? (
        <ContentsWrapper maxWidth="1280px" wrapperTitle="프로젝트">
          <ProjectContainer endPoint={endPoint} />
        </ContentsWrapper>
      ) : (
        <ContentsWrapper maxWidth="1280px" wrapperTitle="코드">
          <CodeContainer endPoint={endPoint} />
        </ContentsWrapper>
      )} */}

      {/* <ContentsWrapper maxWidth="1280px" wrapperTitle="프로젝트">
        <ProjectContainer endPoint={endPoint} />
      </ContentsWrapper>
      <ContentsWrapper maxWidth="1280px" wrapperTitle="코드">
        <CodeContainer endPoint={endPoint} />
      </ContentsWrapper> */}
    </CommonLayout>
  );
};
