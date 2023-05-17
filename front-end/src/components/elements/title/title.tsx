import { TitleContainer, TitleText, Description } from "./style";

interface TitleProps {
  title: string;
  description: string;
  color?: string;
}

export const Title = ({ title, description, color }: TitleProps) => {
  return (
    <TitleContainer>
      <TitleText color={color}>{title}</TitleText>
      <Description color={color}>{description}</Description>
    </TitleContainer>
  );
};
