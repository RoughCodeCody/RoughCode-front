import { FlexDiv, Nickname, TagChipSub, Text } from "@/components/elements";
import { Count } from "@/components/count";
import { UrlApkBtn } from "./style";
import { useState } from "react";

export const PortfolioInfo = () => {
  // 더미데이터
  const title = "개발새발";
  const url = "https://rough-code.com";
  const nickname = "닉네임";
  const defaultLikeCnt = 0;
  const isLiked = true;
  const defaultBMCnt = 0;
  const isBookmarked = false;
  const tagList = ["next.js", "spring boot"];

  const [likeCnt, setLikeCnt] = useState<number>(defaultLikeCnt);
  const [bookmarkCnt, setBookmarkCnt] = useState<number>(defaultBMCnt);

  return (
    <>
      <FlexDiv direction="column" gap="1rem">
        <FlexDiv width="100%" justify="space-between">
          <FlexDiv align="end">
            <Text size="2.25rem">{title}</Text>
            <Nickname nickname={nickname} color="main" />
          </FlexDiv>
          <FlexDiv>
            <Count
              type="like"
              cnt={likeCnt}
              setCnt={setLikeCnt}
              isChecked={isLiked}
            />
            <Count
              type="bookmark"
              cnt={bookmarkCnt}
              setCnt={setBookmarkCnt}
              isChecked={isBookmarked}
            />
          </FlexDiv>
        </FlexDiv>
        <FlexDiv width="100%" justify="start">
          {tagList.map((val, idx) => (
            <TagChipSub tag={val} key={idx} />
          ))}
        </FlexDiv>
        <UrlApkBtn>{url}</UrlApkBtn>
      </FlexDiv>
    </>
  );
};
