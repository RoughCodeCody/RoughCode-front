import { FlexDiv } from "./flexdiv";
import { BsPerson } from "react-icons/bs";
import { Text } from "./text";

type NicknameProps = {
  nickname: string;
  color?: string;
};

export const Nickname = ({ nickname, color }: NicknameProps) => {
  return (
    <FlexDiv>
      <BsPerson
        color={color ? "var(--" + color + "-color)" : "var(--font-color)"}
      />
      <Text as="span" color={color}>
        {nickname}
      </Text>
    </FlexDiv>
  );
};
