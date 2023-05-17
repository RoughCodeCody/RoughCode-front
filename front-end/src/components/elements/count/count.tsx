import { MouseEventHandler } from "react";
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
  isChecked: boolean | null;
  cnt: number;
  onClickFunc?: MouseEventHandler;
}

// like, bookmark, code 카운트 표시
// type === "code"일 때는 onClickFunc props null로 지정할 것
export const Count = ({ type, isChecked, cnt, onClickFunc }: CountProps) => {
  return (
    <FlexDiv padding="0 0.5rem">
      {type === "like" ? (
        <IconWrapper color="red" pointer={true} onClick={onClickFunc}>
          {isChecked ? <TiHeartFullOutline /> : <TiHeartOutline />}
        </IconWrapper>
      ) : type === "bookmark" ? (
        <IconWrapper color="main" pointer={true} onClick={onClickFunc}>
          {isChecked ? <TiStarFullOutline /> : <TiStarOutline />}
        </IconWrapper>
      ) : (
        <IconWrapper color="font" pointer={false}>
          {<RiCodeSSlashFill />}
        </IconWrapper>
      )}
      <Text as="span" padding="0 0 0 0.3rem" lineHeight="1.5rem">
        {cnt}
      </Text>
    </FlexDiv>
  );
};
