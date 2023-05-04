import { BsPerson } from "react-icons/bs";
import { FlexDiv } from "../flexdiv";
import { Text } from "../text";

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
      <Text as="span" color={color} padding="0 0 0 0.1rem">
        {nickname}
      </Text>
    </FlexDiv>
  );
};
