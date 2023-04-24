import {
  TiHeartFullOutline,
  TiHeartOutline,
  TiStarFullOutline,
  TiStarOutline,
} from "react-icons/ti";
import { RiCodeSSlashFill } from "react-icons/ri";
import { FlexDiv, Text } from "../elements";
import { CountIcon } from "./count-icon";

interface CountProps {
  type: "like" | "bookmark" | "code";
  isChecked?: boolean;
  cnt: number;
  setCnt: React.Dispatch<React.SetStateAction<number>>;
}

// like, bookmark, code 카운트 표시
export const Count = ({ type, isChecked, cnt, setCnt }: CountProps) => {
  return (
    <FlexDiv padding="0.5rem">
      {type === "like" ? (
        <CountIcon
          color="red"
          icon={isChecked ? <TiHeartFullOutline /> : <TiHeartOutline />}
        />
      ) : type === "bookmark" ? (
        <CountIcon
          color="main"
          icon={isChecked ? <TiStarFullOutline /> : <TiStarOutline />}
        />
      ) : (
        <CountIcon color="font" icon={<RiCodeSSlashFill />} />
      )}
      <Text as="span" padding="0 0 0 0.3rem">
        {cnt}
      </Text>
    </FlexDiv>
  );
};
