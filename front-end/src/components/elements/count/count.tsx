import {
  TiHeartFullOutline,
  TiHeartOutline,
  TiStarFullOutline,
  TiStarOutline,
} from "react-icons/ti";
import { RiCodeSSlashFill } from "react-icons/ri";
import { FlexDiv } from "../flexdiv";
import { Text } from "../text";
import { IconWrapper } from "./style";

interface CountProps {
  type: "like" | "bookmark" | "code";
  isChecked?: boolean;
  cnt: number;
  setCnt: React.Dispatch<React.SetStateAction<number>>;
}

// like, bookmark, code 카운트 표시
export const Count = ({ type, isChecked, cnt, setCnt }: CountProps) => {
  return (
    <FlexDiv padding="0 0.5rem">
      {type === "like" ? (
        <IconWrapper color="red" pointer={true}>
          {isChecked ? <TiHeartFullOutline /> : <TiHeartOutline />}
        </IconWrapper>
      ) : type === "bookmark" ? (
        <IconWrapper color="main" pointer={true}>
          {isChecked ? <TiStarFullOutline /> : <TiStarOutline />}
        </IconWrapper>
      ) : (
        <IconWrapper color="font" pointer={false}>
          {<RiCodeSSlashFill />}
        </IconWrapper>
      )}
      <Text as="span" padding="0 0 0 0.3rem">
        {cnt}
      </Text>
    </FlexDiv>
  );
};
