import { useEffect, useState } from "react";

import { DropdownArrow } from "@/components/elements";
import { FlexDiv, TagChip } from "@/components/elements";

import { SearchInput, TagSelectContainer } from "./style";
import { TagSelectItem } from "./tag-select-item";

type SelectedTags = string[];

export const TagSearch = () => {
  // 더미데이터
  const dummyTags: SelectedTags = [
    "자바",
    "리액트",
    "파이썬",
    "씨쁠쁠",
    "스프링",
    "자바",
    "리액트",
    "파이썬",
    "씨쁠쁠",
    "스프링",
    "자바",
    "리액트",
    "파이썬",
    "씨쁠쁠",
    "스프링",
    "자바",
    "리액트",
    "파이썬",
    "씨쁠쁠",
    "스프링",
  ];

  const [selectedTags, setSelectedTags] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  useEffect(() => console.log("ㄴㅁㅇㅎㅁㄴㅇㄹ"), [isOpen]);

  return (
    <FlexDiv
      width="100%"
      maxWidth="70rem"
      justify="space-between"
      align="start"
      gap="1rem"
    >
      <FlexDiv
        gap="1rem"
        width="100%"
        height="100%"
        justify="start"
        wrap="wrap"
      >
        {selectedTags.length == 0 ? (
          <TagChip tag={"없음"} />
        ) : (
          selectedTags.map((tag) => <TagChip tag={tag} key={tag} />)
        )}
      </FlexDiv>
      <FlexDiv
        position="relative"
        width="100%"
        height="3.7rem"
        maxWidth="17.5rem"
        maxHeight="3.7rem"
        padding="0 1.1rem 0 1.1rem"
        justify="space-between"
        radius="8px"
        color="white"
        pointer={true}
        shadow={true}
      >
        <SearchInput onFocus={() => setIsOpen(true)} placeholder="태그 검색" />
        <DropdownArrow
          isOpen={isOpen}
          size={30}
          onClick={() => setIsOpen(!isOpen)}
        />
        {isOpen ? (
          <TagSelectContainer>
            {dummyTags.map((tag) => (
              <TagSelectItem tag={tag} />
            ))}
          </TagSelectContainer>
        ) : (
          <></>
        )}
      </FlexDiv>
    </FlexDiv>
  );
};
