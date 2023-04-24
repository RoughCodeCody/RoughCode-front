import { FlexDiv } from "@/components/elements";
import { SearchInput } from "./style";
import { TagChip } from "@/components/elements/tag-chip/tag-chip";

type SelectedTags = string[];

export const TagSearch = () => {
  // 더미데이터
  const SelectedTags: SelectedTags = [
    "자바",
    "리액트",
    "파이썬",
    "씨쁠쁠",
    "스프링",
  ];

  return (
    <FlexDiv
      width="100%"
      maxWidth="1112px"
      justify="space-between"
      align="start"
      gap="16px"
    >
      <FlexDiv
        gap="1rem"
        width="100%"
        height="100%"
        justify="start"
        wrap="wrap"
      >
        {SelectedTags.length == 0 ? (
          <TagChip tag={"전체"} />
        ) : (
          SelectedTags.map((tag) => <TagChip tag={tag} key={tag} />)
        )}
      </FlexDiv>
      <SearchInput padding="0 1.1rem 0 1.1rem" placeholder="태그 검색" />
    </FlexDiv>
  );
};
