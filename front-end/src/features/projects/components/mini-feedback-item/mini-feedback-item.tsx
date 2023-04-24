import { FlexDiv, Nickname, TagChipSub, Text } from "@/components/elements";
import { MiniFeedbackItemWrapper } from "./style";

type MiniFeedbackItemProps = {
  user: string;
  content: string;
  tags?: string[];
};

export const MiniFeedbackItem = ({
  user,
  content,
  tags,
}: MiniFeedbackItemProps) => {
  return (
    <MiniFeedbackItemWrapper>
      <Nickname nickname={user} />
      <FlexDiv direction="column" gap="0.5rem" align="start">
        <Text>{content}</Text>
        {tags?.map((tag) => (
          <TagChipSub tag={tag} />
        ))}
      </FlexDiv>
    </MiniFeedbackItemWrapper>
  );
};
