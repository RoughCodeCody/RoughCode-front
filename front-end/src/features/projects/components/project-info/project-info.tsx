import { useState } from "react";

import {
  FlexDiv,
  Nickname,
  TagChipSub,
  Text,
  Count,
} from "@/components/elements";

import { useCheckURLOpen } from "../../api";
import { UrlApkBtn } from "./style";

type ProjectInfoProps = {
  data: {
    title: string;
    userName: string;
    url: string;
    liked: boolean;
    likeCnt: number;
    favorite: boolean;
    favoriteCnt: number;
    tags: string[];
  };
  projectId: string;
};

export const ProjectInfo = ({
  data: { title, userName, url, liked, likeCnt, favorite, favoriteCnt, tags },
  projectId,
}: ProjectInfoProps) => {
  const [isLiked, setisLiked] = useState(liked);
  const [newLikeCnt, setNewLikeCnt] = useState(likeCnt);
  const [isBookmarked, setIsBookmarked] = useState(favorite);
  const [bookmarkCnt, setBookmarkCnt] = useState(favoriteCnt);

  // URL 버튼 클릭시 서버 running 여부 확인한 후 연결
  const checkURLQuery = useCheckURLOpen();
  const handleURLAPKBtnClick = () => {
    checkURLQuery.mutate(projectId, {
      onSuccess: (data) => {
        console.log("res", data);
        if (data === 1) window.open(url, "_blank");
        else alert("프로젝트 서버가 닫혀있어요");
      },
    });
  };

  return (
    <>
      <FlexDiv direction="column" gap="1rem">
        <FlexDiv width="100%" justify="space-between">
          <FlexDiv>
            <Text size="2.25rem" margin="0 1rem 0 0">
              {title}
            </Text>
            <Nickname nickname={userName} color="main" />
          </FlexDiv>
          <FlexDiv>
            <Count
              type="like"
              cnt={newLikeCnt}
              setCnt={setNewLikeCnt}
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
          {tags.map((val, idx) => (
            <TagChipSub tag={val} key={idx} />
          ))}
        </FlexDiv>
        <UrlApkBtn onClick={handleURLAPKBtnClick}>{url}</UrlApkBtn>
      </FlexDiv>
    </>
  );
};
