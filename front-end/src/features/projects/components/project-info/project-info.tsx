import { useState } from "react";

import {
  FlexDiv,
  Nickname,
  TagChipSub,
  Text,
  Count,
} from "@/components/elements";

import {
  useCheckURLOpen,
  usePutProjectOpenStatus,
  usePostProjectLike,
  usePostProjectFav,
} from "../../api";
import { ProjectInfoResult } from "../../types";
import { UrlApkBtn } from "./style";

type ProjectInfoProps = {
  data: ProjectInfoResult;
  projectId: string;
};

export const ProjectInfo = ({
  data: {
    title,
    userName,
    url,
    liked,
    likeCnt,
    favorite,
    favoriteCnt,
    tags,
    mine,
  },
  projectId,
}: ProjectInfoProps) => {
  // const [isLiked, setisLiked] = useState(liked);
  // const [newLikeCnt, setNewLikeCnt] = useState(likeCnt);
  // const [isBookmarked, setIsBookmarked] = useState(favorite);
  // const [bookmarkCnt, setBookmarkCnt] = useState(favoriteCnt);

  // 더미데이터
  // const mine = true;

  // 프로젝트 좋아요/좋아요 취소
  const postProjectLikeQuery = usePostProjectLike();

  // 프로젝트 즐겨찾기/즐겨찾기 취소
  const postProjectFavQuery = usePostProjectFav();

  // 프로젝트 열기/닫기
  const putProjectOpenStatusQuery = usePutProjectOpenStatus();

  const mine = true;

  // 프로젝트 열기/닫기
  const putProjectOpenStatusQuery = usePutProjectOpenStatus();

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
              cnt={likeCnt}
              // setCnt={setNewLikeCnt}
              isChecked={liked}
              // setIsChecked={setisLiked}
              onClickFunc={() => postProjectLikeQuery.mutate(projectId)}
            />
            <Count
              type="bookmark"
              cnt={favoriteCnt}
              // setCnt={setBookmarkCnt}
              isChecked={favorite}
              // setIsChecked={setIsBookmarked}
              onClickFunc={() => postProjectFavQuery.mutate(projectId)}
            />
          </FlexDiv>
        </FlexDiv>
        <FlexDiv width="100%" justify="start">
          {tags.map((val, idx) => (
            <TagChipSub tag={val} key={idx} />
          ))}
        </FlexDiv>
        <FlexDiv
          width="100%"
          direction="column"
          align="end"
          margin="1rem 0 0 0"
        >
          {mine && (
            <Text
              pointer={true}
              onClick={() =>
                putProjectOpenStatusQuery.mutate({
                  projectId,
                  status: closed ? "open" : "close",
                })
              }
            >
              {closed ? "프로젝트 열기" : "프로젝트 닫기"}
            </Text>
          )}
          <UrlApkBtn onClick={handleURLAPKBtnClick}>{url}</UrlApkBtn>
        </FlexDiv>
      </FlexDiv>
    </>
  );
};
