import { MouseEventHandler } from "react";
import { BtnWrapper } from "./style";

interface BtnProps {
  text: string | undefined;
  bgColor?: string;
  color?: string;
  padding?: string;
  margin?: string;
  fontSize?: string;
  width?: string;
  height?: string;
  display?: string;
  justify?: string;
  align?: string;
  onClickFunc?: MouseEventHandler<HTMLButtonElement>;
}

export const Btn = ({
  text,
  bgColor,
  color,
  padding,
  margin,
  fontSize,
  width,
  height,
  display,
  justify,
  align,
  onClickFunc,
}: BtnProps) => {
  return (
    <BtnWrapper
      bgColor={bgColor}
      color={color}
      padding={padding}
      margin={margin}
      fontSize={fontSize}
      width={width}
      height={height}
      display={display}
      justify={justify}
      align={align}
      onClick={onClickFunc}
    >
      {text}
    </BtnWrapper>
  );
};
