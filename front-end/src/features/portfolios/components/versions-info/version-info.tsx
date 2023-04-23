import { FlexDiv, Nickname, Text, WhiteBoxNoshad } from "@/components/elements";
import { VersionInfoWrapper } from "./style";
import { FeedbackItem } from "../feedback-item";

type VersionInfoProps = {
  version: string;
  notice: string;
  current: boolean;
  feedbacks: { user: string; content: string }[];
};

export const VersionInfo = ({
  version,
  notice,
  current,
  feedbacks,
}: VersionInfoProps) => {
  return (
    <VersionInfoWrapper bgColor={current ? "sub-one" : "white"}>
      <FlexDiv width="5%">
        <Text as="span" color={current ? "main" : "font"} bold={current}>
          {version}
        </Text>
      </FlexDiv>
      <FlexDiv width="90%" direction="column" gap="0.7rem">
        <Text
          color={current ? "main" : "font"}
          bold={current}
          style={{ alignSelf: "start", marginBottom: "0.5rem" }}
        >
          {notice}
        </Text>
        {feedbacks.length !== 0 && (
          <>
            <Text>반영한 피드백</Text>
            {feedbacks.map(({ user, content }) => (
              <FeedbackItem user={user} content={content} />
            ))}
          </>
        )}
      </FlexDiv>
    </VersionInfoWrapper>
  );
};
