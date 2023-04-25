import { Dispatch, SetStateAction } from "react";
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
  setIsChecked?: Dispatch<SetStateAction<boolean>>;
  cnt: number;
  setCnt: Dispatch<SetStateAction<number>>;
}

// like, bookmark, code 카운트 표시

export const Count = ({
  type,
  isChecked,
  setIsChecked,
  cnt,
  setCnt,
}: CountProps) => {
  // 좋아요, 북마크 아이콘 클릭시 상태 및 카운트 변경
  const handleIconClick = () => {
    if (isChecked && setIsChecked) {
      // api 요청 성공시
      setIsChecked(false);
      setCnt((prev) => prev - 1);
    } else if (!isChecked && setIsChecked) {
      // api 요청 성공시
      setIsChecked(true);
      setCnt((prev) => prev + 1);
    }
  };

  return (
    <FlexDiv padding="0 0.5rem">
      {type === "like" ? (
        <IconWrapper color="red" pointer={true} onClick={handleIconClick}>
          {isChecked ? <TiHeartFullOutline /> : <TiHeartOutline />}
        </IconWrapper>
      ) : type === "bookmark" ? (
        <IconWrapper color="main" pointer={true} onClick={handleIconClick}>
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
