import Image from "next/image";
import { FlexDiv, Text } from "@/components/elements";

export const ProjectCard = () => {
  return (
    <FlexDiv
      width="24rem"
      height="32rem"
      direction="column"
      border="solid 1px var(--shad-color)"
      radius="32px"
      paddingY="2rem"
    >
      <FlexDiv padding="0 1.2rem 2rem 1.2rem">
        <Image
          src="https://picsum.photos/400/300"
          layout="responsive"
          width={500}
          height={300}
          alt={"PJT"}
        />
      </FlexDiv>
      <FlexDiv
        width="100%"
        height="0"
        border="solid 1px var(--shad-color)"
      ></FlexDiv>
      <FlexDiv
        width="100%"
        height="100%"
        paddingX="1.8rem"
        direction="column"
        justify="start"
        align="start"
      >
        <FlexDiv paddingY="0.5rem">
          <Text size="1.3rem" bold={true}>
            title
          </Text>
        </FlexDiv>
        <FlexDiv width="100%" height="100%" direction="column" align="start">
          <Text width="100%" height="60%" wrap="break-word">
            descriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescript
          </Text>
          <Text width="100%" height="40%">
            tag
          </Text>
        </FlexDiv>
      </FlexDiv>
    </FlexDiv>
  );
};
