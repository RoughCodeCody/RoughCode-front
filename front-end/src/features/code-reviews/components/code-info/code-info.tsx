import { useRouter } from "next/router";
import { useState } from "react";
import { AiOutlineLink } from "react-icons/ai";

import {
  Btn,
  Count,
  FlexDiv,
  Nickname,
  TagChipSub,
  Text,
  Selection,
  Modal,
} from "@/components/elements";

import { usePostCodeLike, usePostCodeFav, useDeleteCode } from "../../api";
import { CodeInfoResult, codeForFeedbackModify } from "../../types";
import { DeleteCode } from "./delete-code";

interface CodeInfoProps {
  data: CodeInfoResult | codeForFeedbackModify;
  isMine: boolean;
  isLatest: boolean;
}

export const CodeInfo = ({
  data: {
    codeId,
    version,
    title,
    likeCnt,
    liked,
    favoriteCnt,
    favorite,
    tags,
    userName,
    projectTitle,
    projectId,
  },
  isMine,
  isLatest,
}: CodeInfoProps) => {
  const router = useRouter();
  const isWriting =
    router.asPath.includes("create") || router.asPath.includes("modify");

  // 삭제 확인 모달 관련 state
  const [codeDeleteModalOpen, setCodeDeleteModalOpen] = useState(false);

  // selection 선택시 닫기 위한 state
  const [forceClose, setForceClose] = useState(false);

  // 코드 좋아요/좋아요 취소
  const codeLikeQuery = usePostCodeLike();

  // 코드 즐겨찾기/즐겨찾기 취소
  const codeFavQuery = usePostCodeFav();

  // 코드 삭제
  const deleteCodeQuery = useDeleteCode();
  const handleDelete = () => {
    deleteCodeQuery.mutate(codeId);
    router.replace("/code-review");
  };

  return (
    <>
      <FlexDiv direction="column" gap="2rem" width="100%">
        <FlexDiv direction="column" width="100%" gap="0.8rem">
          <FlexDiv width="100%" justify="space-between">
            <FlexDiv>
              <Text
                size="2.25rem"
                color="main"
                bold={true}
              >{`V${version}`}</Text>
              <Text size="2.25rem" margin="0 1rem">
                {title}
              </Text>
              <Nickname nickname={userName} color="main" />
            </FlexDiv>
            <FlexDiv>
              <Count
                type="like"
                cnt={likeCnt}
                isChecked={liked}
                onClickFunc={() => codeLikeQuery.mutate(codeId)}
              />
              <Count
                type="bookmark"
                cnt={favoriteCnt}
                isChecked={favorite}
                onClickFunc={() => codeFavQuery.mutate(codeId)}
              />
              {isMine && isLatest && (
                <Selection
                  selectionList={{
                    수정하기: () => {},
                    삭제하기: () => {
                      setCodeDeleteModalOpen(true);
                      setForceClose(true);
                    },
                  }}
                  forceClose={forceClose}
                />
              )}
            </FlexDiv>
          </FlexDiv>
          <FlexDiv width="100%" justify="start" wrap="wrap" gap="0.5rem">
            {tags.map((val, idx) => (
              <TagChipSub tag={val} key={idx} />
            ))}
          </FlexDiv>
        </FlexDiv>

        <FlexDiv width="100%" direction="row-reverse" justify="space-between">
          {isWriting ? (
            <Btn
              bgColor="orange"
              color="bg"
              text="이 코드에 리뷰 작성 중"
              disabled={true}
            />
          ) : (
            <Btn
              text="이 코드에 리뷰 작성하기"
              onClickFunc={() =>
                router.push(`/code-review/review/create/${codeId}`)
              }
            />
          )}

          {projectId && (
            <FlexDiv justify="start">
              <AiOutlineLink fontSize={24} />
              <Text size="1.2rem" bold={true}>
                연결된 프로젝트
              </Text>
              <Text
                size="1.2rem"
                bold={true}
                color="main"
                margin="0 1rem"
                pointer={true}
                onClick={() => router.push(`/project/${projectId}`)}
              >
                {projectTitle}
              </Text>
            </FlexDiv>
          )}
        </FlexDiv>
      </FlexDiv>

      <Modal
        headerText={"코드 삭제"}
        height="10rem"
        isOpen={codeDeleteModalOpen}
        setIsOpen={setCodeDeleteModalOpen}
        setForceClose={setForceClose}
        modalContent={
          <DeleteCode
            codeTitle={title}
            setModalOpen={setCodeDeleteModalOpen}
            handleDelete={handleDelete}
            setForceClose={setForceClose}
          />
        }
      />
    </>
  );
};
