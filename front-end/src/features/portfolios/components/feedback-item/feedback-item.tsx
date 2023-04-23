import { FlexDiv, Nickname, TagChipSub, Text } from "@/components/elements";
import { FeedbackItemWrapper } from "./style";

type FeedbackItemProps = {
  user: string;
  content: string;
  tags?: string[];
};

export const FeedbackItem = ({ user, content, tags }: FeedbackItemProps) => {
  return (
    <FeedbackItemWrapper>
      <Nickname nickname={user} />
      <FlexDiv direction="column" gap="0.5rem" align="start">
        <Text>{content}</Text>
        {tags?.map((tag) => (
          <TagChipSub tag={tag} />
        ))}
      </FlexDiv>
    </FeedbackItemWrapper>
  );
};
