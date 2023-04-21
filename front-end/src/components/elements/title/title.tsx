import { describe } from "node:test";
import { TitleContainer, TitleText, Description } from "./style";

interface TitleProps {
  title: string;
  description: string;
}

export const Title = ({ title, description }: TitleProps) => {
  return (
    <>
      <TitleContainer>
        <TitleText>{title}</TitleText>
        <Description>{description}</Description>
      </TitleContainer>
    </>
  );
};
