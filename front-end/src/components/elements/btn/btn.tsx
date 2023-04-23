import { MouseEventHandler } from "react";
import { BtnWrapper } from "./style";

interface BtnProps {
  text: string | undefined;
  bgColor?: string;
  color?: string;
  padding?: string;
  margin?: string;
  fontSize?: string;
  onClickFunc?: MouseEventHandler<HTMLButtonElement>;
}

export const Btn = ({
  text,
  bgColor,
  color,
  padding,
  margin,
  fontSize,
  onClickFunc,
}: BtnProps) => {
  return (
    <BtnWrapper
      bgColor={bgColor}
      color={color}
      padding={padding}
      margin={margin}
      fontSize={fontSize}
      onClick={onClickFunc}
    >
      {text}
    </BtnWrapper>
  );
};
