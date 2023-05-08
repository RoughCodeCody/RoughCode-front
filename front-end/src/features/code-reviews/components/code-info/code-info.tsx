import { useRouter } from "next/router";
import { AiOutlineLink } from "react-icons/ai";

import {
  Btn,
  Count,
  FlexDiv,
  Nickname,
  TagChipSub,
  Text,
} from "@/components/elements";
import { Selection } from "@/components/selection";

import { usePostCodeLike } from "../../api";
import { Code } from "../../types";

interface CodeInfoProps {
  data: Code;
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
}: CodeInfoProps) => {
  const router = useRouter();

  // 코드 좋아요/좋아요 취소
  const codeLikeQuery = usePostCodeLike();

  return (
    <>
      <FlexDiv direction="column" gap="2rem">
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
                // setCnt={setNewLikeCnt}
                isChecked={liked}
                // setIsChecked={setisLiked}
                onClickFunc={() => codeLikeQuery.mutate(codeId)}
              />
              <Count
                type="bookmark"
                cnt={favoriteCnt}
                // setCnt={setBookmarkCnt}
                isChecked={favorite}
                // setIsChecked={setIsBookmarked}
                onClickFunc={() => {}}
              />
              <Selection isMine={true} />
            </FlexDiv>
          </FlexDiv>
          <FlexDiv width="100%" justify="start">
            {tags.map((val, idx) => (
              <TagChipSub tag={val} key={idx} />
            ))}
          </FlexDiv>
        </FlexDiv>

        <FlexDiv width="100%" direction="row-reverse" justify="space-between">
          <Btn text="이 코드에 리뷰 작성하기" onClickFunc={() => {}} />
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
    </>
  );
};
