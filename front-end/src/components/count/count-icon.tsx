import { ReactNode } from "react";
import { IconWrapper } from "./style";

interface CountIconProps {
  color: string;
  icon: ReactNode;
}

// type에 따라서 보여줄 react icon을 리턴
export const CountIcon = ({ color, icon }: CountIconProps) => {
  return <IconWrapper color={color}>{icon}</IconWrapper>;
};
