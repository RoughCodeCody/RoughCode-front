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
  isChecked: boolean | null;
  // setIsChecked: Dispatch<SetStateAction<boolean>> | null;
  cnt: number;
  // setCnt: Dispatch<SetStateAction<number>> | null;
  onClickFunc: () => void;
}

// like, bookmark, code 카운트 표시
// type === "code"일 때는 isChecked, setIsChecked props null로 지정할 것
export const Count = ({
  type,
  isChecked,
  // setIsChecked,
  cnt,
  // setCnt,
  onClickFunc,
}: CountProps) => {
  // // 좋아요, 북마크 아이콘 클릭시 상태 및 카운트 변경
  // const handleIconClick = () => {
  //   if (!setIsChecked || !setCnt) return;

  //   if (isChecked) {
  //     // api 요청 성공시
  //     setIsChecked(false);
  //     setCnt((prev) => prev - 1);
  //   } else {
  //     // api 요청 성공시
  //     setIsChecked(true);
  //     setCnt((prev) => prev + 1);
  //   }
  // };

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
