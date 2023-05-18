import { useRouter } from "next/router";
import { useState } from "react";

import {
  FlexDiv,
  Nickname,
  TagChipSub,
  Text,
  Count,
  Selection,
  Modal,
  LoadingSpinner,
} from "@/components/elements";

import {
  useCheckURLOpen,
  usePutProjectOpenStatus,
  usePostProjectLike,
  usePostProjectFav,
  useDeleteProject,
} from "../../api";
import { ProjectInfoResult } from "../../types";
import { DeleteProject } from "./delete-project";
import { UrlApkBtn } from "./style";

type ProjectInfoProps = {
  data: ProjectInfoResult;
  projectId: string;
  isLatest: boolean;
};

export const ProjectInfo = ({
  data: {
    title,
    userName,
    url,
    closed,
    liked,
    likeCnt,
    favorite,
    favoriteCnt,
    tags,
    mine,
  },
  projectId,
  isLatest,
}: ProjectInfoProps) => {
  const router = useRouter();

  const [projectDeleteModalOpen, setProjectDeleteModalOpen] = useState(false);

  // 프로젝트 좋아요/좋아요 취소
  const postProjectLikeQuery = usePostProjectLike();

  // 프로젝트 즐겨찾기/즐겨찾기 취소
  const postProjectFavQuery = usePostProjectFav();

  // 프로젝트 열기/닫기
  const putProjectOpenStatusQuery = usePutProjectOpenStatus();

  // 프로젝트 삭제
  const deleteProjectQuery = useDeleteProject();
  const handleDelete = () => {
    deleteProjectQuery.mutate(Number(projectId));
    router.replace("/project");
  };

  // URL 버튼 클릭시 서버 running 여부 확인한 후 연결
  const checkURLQuery = useCheckURLOpen();
  const handleURLAPKBtnClick = () => {
    if (closed) return;

    checkURLQuery.mutate(Number(projectId), {
      onSuccess: (data) => {
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
              isChecked={liked}
              onClickFunc={() => postProjectLikeQuery.mutate(Number(projectId))}
            />
            <Count
              type="bookmark"
              cnt={favoriteCnt}
              isChecked={favorite}
              onClickFunc={() => postProjectFavQuery.mutate(Number(projectId))}
            />
            {mine && isLatest && (
              <Selection
                selectionList={{
                  수정하기: () => {
                    router.push(`/project/modify/${projectId}`);
                  },
                  삭제하기: () => setProjectDeleteModalOpen(true),
                }}
              />
            )}
          </FlexDiv>
        </FlexDiv>

        <FlexDiv width="100%" justify="start" wrap="wrap" gap="0.5rem">
          {tags.map(({ name, tagId }) => (
            <TagChipSub tag={name} key={tagId} />
          ))}
        </FlexDiv>

        <FlexDiv
          width="100%"
          direction="column"
          align="end"
          margin="1rem 0 0 0"
        >
          {mine && isLatest && (
            <Text
              pointer={true}
              onClick={() =>
                putProjectOpenStatusQuery.mutate({
                  projectId: Number(projectId),
                  status: closed ? "open" : "close",
                })
              }
            >
              {closed ? "프로젝트 열기" : "프로젝트 닫기"}
            </Text>
          )}
          <UrlApkBtn isClosed={closed} onClick={handleURLAPKBtnClick}>
            {url.length <= 35 ? url : `${url.substring(0, 35)}...`}
          </UrlApkBtn>
        </FlexDiv>
      </FlexDiv>

      <Modal
        headerText={"프로젝트 삭제"}
        height="10rem"
        isOpen={projectDeleteModalOpen}
        setIsOpen={setProjectDeleteModalOpen}
        modalContent={
          <DeleteProject
            projectTitle={title}
            setModalOpen={setProjectDeleteModalOpen}
            handleDelete={handleDelete}
          />
        }
      />

      <LoadingSpinner isOpen={Boolean(checkURLQuery.status === "loading")} />
    </>
  );
};
