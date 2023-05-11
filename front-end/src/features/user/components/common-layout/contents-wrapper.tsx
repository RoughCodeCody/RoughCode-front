import { FlexDiv, Text } from "@/components/elements";
import { ReactNode } from "react";

type ContentsWrapper = {
  wrapperTitle: string;
  children: ReactNode;
};

export const ContentsWrapper = ({
  wrapperTitle,
  children,
}: ContentsWrapper) => {
  return (
    <FlexDiv width="100%" height="75vh" direction="column" gap="2rem">
      <FlexDiv width="100%" justify="start" paddingX="3rem">
        <Text size="3rem" bold={true}>
          {wrapperTitle}
        </Text>
      </FlexDiv>
      <FlexDiv width="100%" height="100%" overflow="auto" align="start">
        {children}
      </FlexDiv>
    </FlexDiv>
  );
};
