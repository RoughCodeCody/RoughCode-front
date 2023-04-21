import {
  TiHeartFullOutline,
  TiHeartOutline,
  TiStarFullOutline,
  TiStarOutline,
} from "react-icons/ti";
import { RiCodeSSlashFill } from "react-icons/ri";
import { FlexDiv } from "../flexdiv";
import { Text } from "../text";
import { CountIcon } from "./count-icon";

interface CountProps {
  type: "like" | "bookmark" | "code";
  isChecked?: boolean;
  cnt: number;
  setCnt: React.Dispatch<React.SetStateAction<number>>;
}

// type에 따라서 보여줄 react icon을 리턴
const chooseIcon = (type: string, isChecked?: boolean) => {
  if (type === "like")
    return (
      <CountIcon
        color="red"
        icon={isChecked ? <TiHeartFullOutline /> : <TiHeartOutline />}
      />
    );
  else if (type === "bookmark")
    return (
      <CountIcon
        color="main"
        icon={isChecked ? <TiStarFullOutline /> : <TiStarOutline />}
      />
    );
  else if (type === "code")
    return <CountIcon color="font" icon={<RiCodeSSlashFill />} />;

  //   isChecked ? (
  //     <TiHeartFullOutline color="var(--red-color)" fontSize="1.5rem" />
  //   ) : (
  //     <TiHeartOutline color="var(--red-color)" fontSize="1.5rem" />
  //   );
  // else if (type === "bookmark")
  //   return isChecked ? (
  //     <TiStarFullOutline color="var(--main-color)" fontSize="1.5rem" />
  //   ) : (
  //     <TiStarOutline color="var(--main-color)" fontSize="1.5rem" />
  //   );
  // else if (type === "code")
  //   return <RiCodeSSlashFill color="var(--font-color)" fontSize="1.5rem" />;
};

// like, bookmark, code 카운트 표시
export const Count = ({ type, isChecked, cnt, setCnt }: CountProps) => {
  return (
    <FlexDiv padding="0.5rem">
      {chooseIcon(type, isChecked)}
      <Text as="span" padding="0 0 0 0.3rem">
        {cnt}
      </Text>
    </FlexDiv>
  );
};
