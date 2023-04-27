import { FlexDiv, Text } from "@/components/elements";
import { TooltopWrapper, EditNickNameIcon } from "./style";

export const Welcome = () => {
  // 더미 데이터
  const name = "개발새발";

  return (
    <FlexDiv justify="start" width="100%" padding="0 0 0 3%">
      <Text size="2.3rem">
        <Text as="span" size="2.5rem" bold={true}>
          {name}
        </Text>
        님, 안녕하세요
      </Text>

      <TooltopWrapper aria-label="닉네임 수정하기">
        <EditNickNameIcon />
      </TooltopWrapper>
    </FlexDiv>
  );
};
