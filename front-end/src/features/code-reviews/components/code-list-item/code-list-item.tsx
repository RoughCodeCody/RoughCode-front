import { useRouter } from "next/router";
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
    codeId: number; // 코드 ID
    version: number; // 코드 버전
    title: string; // 코드 제목
    date: Date; // 수정날짜
    likeCnt: number; // 좋아요 수
    reviewCnt: number; // 리뷰 수
    tags: { tagId: number; name: string; cnt: number }[]; // 태그 이름 목록
    userName: string; // 코드 작성자 닉네임
    liked: boolean; // 좋아요 여부
  };
};

export const CodeListItem = ({
  codeListItem: {
    codeId,
    version,
    title,
    date,
    likeCnt,
    reviewCnt,
    tags,
    userName,
    liked,
  },
}: CodeListItemProps) => {
  const router = useRouter();

  return (
    <CodeListItemWrapper onClick={() => router.push(`/code-review/${codeId}`)}>
      <FlexDiv width="100%" justify="space-between" pointer={true}>
        <FlexDiv>
          <Text bold={true} color="main">{`V${version}`}</Text>
          <Text bold={true} padding="0 1rem 0 0.3rem" pointer={true}>
            {title}
          </Text>
          {tags.map((tag, idx) => (
            <TagChipSub tag={tag.name} key={idx} />
          ))}
        </FlexDiv>
        <FlexDiv>
          <Count type="like" isChecked={liked} cnt={likeCnt} />
          <Count type="code" isChecked={null} cnt={reviewCnt} />
        </FlexDiv>
      </FlexDiv>

      <FlexDiv width="100%" justify="start" pointer={true} align="end">
        <Nickname nickname={userName} />
        <Text padding="0 1.5rem" size="0.8rem">
          {dayjs(date).format("YY.MM.DD")}
        </Text>
      </FlexDiv>
    </CodeListItemWrapper>
  );
};
