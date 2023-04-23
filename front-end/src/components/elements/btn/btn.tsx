import { MouseEventHandler } from "react";
import { BtnWrapper } from "./style";

interface BtnProps {
  text: string;
  bgColor?: string;
  color?: string;
  onClickFunc?: MouseEventHandler<HTMLButtonElement>;
}

export const Btn = ({ text, bgColor, color, onClickFunc }: BtnProps) => {
  return (
    <BtnWrapper bgColor={bgColor} color={color} onClick={onClickFunc}>
      {text}
    </BtnWrapper>
  );
};
