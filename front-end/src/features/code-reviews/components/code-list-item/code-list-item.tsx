import { useRouter } from "next/router";
import { useState } from "react";
import dayjs from "dayjs";
import {
  FlexDiv,
  Nickname,
  TagChipSub,
  Text,
  Count,
} from "@/components/elements";
import { CodeListItemWrapper } from "./style";

type CodeListItemProps = {
  codeListItem: {
    codesId: number;
    title: string;
    createdDate: Date;
    modifiedDate: Date;
    like: number;
    isLiked: boolean;
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
    isLiked,
    reviewCnt,
    tag,
    userName,
  },
}: CodeListItemProps) => {
  const router = useRouter();

  return (
    <CodeListItemWrapper
      onClick={() => router.push(`/code-reviews/${codesId}`)}
    >
      <FlexDiv width="100%" justify="space-between" pointer={true}>
        <FlexDiv>
          <Text bold={true} padding="0 1rem 0 0" pointer={true}>
            {title}
          </Text>
          <TagChipSub tag={tag[0]} />
        </FlexDiv>
        <FlexDiv>
          <Count
            type="like"
            isChecked={isLiked}
            setIsChecked={null}
            cnt={like}
            setCnt={null}
          />
          <Count
            type="code"
            isChecked={null}
            setIsChecked={null}
            cnt={reviewCnt}
            setCnt={null}
          />
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
