import dayjs from "dayjs";
import { useState } from "react";

import { Count, FlexDiv, Nickname, Text } from "@/components/elements";
import { Selection } from "@/components/selection";
import { Feedback } from "@/features/projects/types";

import { FeedbackItemWrapper } from "./style";

interface FeedbackItemProps {
  feedback: Feedback;
  type: "feedback" | "review";
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
  type,
}: FeedbackItemProps) => {
  // 더미데이터
  const isMine = false;

  const [newIsLiked, setNewIsLiked] = useState<boolean>(liked);
  const [newLikeCnt, setNewLikeCnt] = useState<number>(like);

  return (
    <FeedbackItemWrapper
      bgColor={Boolean(selected) ? "sub-one" : "white"}
      isMine={isMine}
    >
      <FlexDiv width="100%" justify="space-between">
        <FlexDiv>
          <Nickname nickname={!userName.length ? "익명" : userName} />
          {Boolean(selected) && (
            <Text color="main" bold={true} padding="0 2rem">
              {`${type === "feedback" ? "프로젝트" : "코드"}에 반영됨`}
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
        <Text>
          {!content.length ? "신고되어 가려진 게시물입니다." : content}
        </Text>
        <Text size="0.8rem">{dayjs(date).format("YY.MM.DD HH:MM")}</Text>
      </FlexDiv>
    </FeedbackItemWrapper>
  );
};
