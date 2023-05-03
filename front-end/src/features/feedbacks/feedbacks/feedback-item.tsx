import { useState } from "react";

import { Count, FlexDiv, Nickname, Text } from "@/components/elements";
import { Selection } from "@/components/selection";
import { Feedback } from "@/features/projects/types";

import { FeedbackItemWrapper } from "./style";
import dayjs from "dayjs";

interface FeedbackItemProps {
  feedback: Feedback;
}

export const FeedbackItem = ({
  feedback: {
    feedbackId,
    userId,
    userName,
    content,
    like,
    selected,
    liked,
    date,
  },
}: FeedbackItemProps) => {
  const [newIsLiked, setNewIsLiked] = useState<boolean>(liked);
  const [newLikeCnt, setNewLikeCnt] = useState<number>(like);

  return (
    <FeedbackItemWrapper
      bgColor={isApplied ? "sub-one" : "white"}
      isMine={isMine}
    >
      <FlexDiv width="100%" justify="space-between">
        <FlexDiv>
          <Nickname nickname={userName} />
          {isApplied && (
            <Text color="main" bold={true} padding="0 2rem">
              프로젝트에 반영됨
            </Text>
          )}
        </FlexDiv>
        <FlexDiv>
          <Count
            type="like"
            isChecked={newIsLiked}
            setIsChecked={setNewIsLiked}
            cnt={newLikeCnt}
            setCnt={setNewLikeCnt}
          />
          <Selection isMine={isMine} />
        </FlexDiv>
      </FlexDiv>

      <FlexDiv width="100%" justify="space-between">
        <Text>{content}</Text>
        <Text size="0.8rem">{dayjs(date).format("YY.MM.DD HH:MM")}</Text>
      </FlexDiv>
    </FeedbackItemWrapper>
  );
};
