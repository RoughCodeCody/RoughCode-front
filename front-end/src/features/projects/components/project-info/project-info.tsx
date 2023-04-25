import { useState } from "react";
import {
  FlexDiv,
  Nickname,
  TagChipSub,
  Text,
  Count,
} from "@/components/elements";
import { UrlApkBtn } from "./style";

export const ProjectInfo = () => {
  // 더미데이터
  const title = "개발새발";
  const url = "https://rough-code.com";
  const nickname = "닉네임";
  const defaultLikeCnt = 1;
  const defaultIsLiked = true;
  const defaultBMCnt = 0;
  const defaultIsBookmarked = false;
  const tagList = ["next.js", "spring boot"];

  const [isLiked, setisLiked] = useState<boolean>(defaultIsLiked);
  const [likeCnt, setLikeCnt] = useState<number>(defaultLikeCnt);
  const [isBookmarked, setIsBookmarked] =
    useState<boolean>(defaultIsBookmarked);
  const [bookmarkCnt, setBookmarkCnt] = useState<number>(defaultBMCnt);

  return (
    <>
      <FlexDiv direction="column" gap="1rem">
        <FlexDiv width="100%" justify="space-between">
          <FlexDiv>
            <Text size="2.25rem" margin="0 1rem 0 0">
              {title}
            </Text>
            <Nickname nickname={nickname} color="main" />
          </FlexDiv>
          <FlexDiv>
            <Count
              type="like"
              cnt={likeCnt}
              setCnt={setLikeCnt}
              isChecked={isLiked}
              setIsChecked={setisLiked}
            />
            <Count
              type="bookmark"
              cnt={bookmarkCnt}
              setCnt={setBookmarkCnt}
              isChecked={isBookmarked}
              setIsChecked={setIsBookmarked}
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
