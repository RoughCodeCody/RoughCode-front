import { useState } from "react";
import dayjs from "dayjs";
import { FlexDiv, Nickname, TagChipSub, Text } from "@/components/elements";
import { Count } from "@/components/count";
import { CodeListItemWrapper } from "./style";

type CodeListItemProps = {
  codeListItem: {
    codesId: number;
    title: string;
    createdDate: Date;
    modifiedDate: Date;
    like: number;
    favorite: number;
    reviewCnt: number;
    tag: string[];
    userName: string;
  };
};

export const CodeListItem = ({
  codeListItem: {
    codesId,
    title,
    createdDate,
    modifiedDate,
    like,
    favorite,
    reviewCnt,
    tag,
    userName,
  },
}: CodeListItemProps) => {
  const [newLikeCnt, setNewLikeCnt] = useState<number>(like);
  const [newReviewCnt, setNewReviewCnt] = useState<number>(reviewCnt);

  return (
    <CodeListItemWrapper>
      <FlexDiv width="100%" justify="space-between" pointer={true}>
        <FlexDiv>
          <Text bold={true} padding="0 1rem 0 0" pointer={true}>
            {title}
          </Text>
          <TagChipSub tag={tag[0]} />
        </FlexDiv>
        <FlexDiv>
          <Count type="like" cnt={newLikeCnt} setCnt={setNewLikeCnt} />
          <Count type="code" cnt={newReviewCnt} setCnt={setNewReviewCnt} />
        </FlexDiv>
      </FlexDiv>

      <FlexDiv width="100%" justify="start" pointer={true}>
        <Nickname nickname={userName} />
        <Text padding="0 1.5rem">
          {dayjs(modifiedDate).format("YY.MM.DD") ||
            dayjs(createdDate).format("YY.MM.DD")}
        </Text>
      </FlexDiv>
    </CodeListItemWrapper>
  );
};
