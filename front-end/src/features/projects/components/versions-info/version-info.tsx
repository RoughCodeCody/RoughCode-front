import { FlexDiv, Text } from "@/components/elements";

import { MiniFeedbackItem } from "../mini-feedback-item";
import { VersionInfoWrapper } from "./style";

type VersionInfoProps = {
  version: number;
  notice: string;
  current: boolean;
  // feedbacks: { user: string; content: string }[];
  feedbacks: number[];
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
          {`V${version}`}
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
            {feedbacks.map(({ user, content }, idx) => (
              <MiniFeedbackItem user={user} content={content} key={idx} />
            ))}
          </>
        )}
      </FlexDiv>
    </VersionInfoWrapper>
  );
};
