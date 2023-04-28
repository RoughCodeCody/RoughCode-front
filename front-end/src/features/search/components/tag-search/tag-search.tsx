import { useEffect, useState } from "react";

import { DropdownArrow } from "@/components/elements";
import { FlexDiv, TagChip } from "@/components/elements";

import { SearchInput, TagSelectContainer } from "./style";
import { TagSelectItem } from "./tag-select-item";
import { useSearchCriteriaStore } from "@/stores";
import { stringify } from "querystring";

type SelectedTags = string[];

export const TagSearch = () => {
  const { searchCriteria, addTagId } = useSearchCriteriaStore();

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

  const [selectedTags, setSelectedTags] = useState<SelectedTags>([]);
  const [isOpen, setisOpen] = useState<boolean>(false);

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
        {searchCriteria.tagIdList.map((tag) => (
          <TagChip tag={tag} key={tag} />
        ))}
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
        <SearchInput onFocus={() => setisOpen(true)} placeholder="태그 검색" />
        <DropdownArrow
          isOpen={isOpen}
          size={30}
          onClick={() => setisOpen(!isOpen)}
        />
        {isOpen ? (
          <TagSelectContainer>
            {dummyTags.map((tag) => (
              <TagSelectItem
                key={tag}
                tag={tag}
                selectedTags={selectedTags}
                setSelectedTags={setSelectedTags}
              />
            ))}
          </TagSelectContainer>
        ) : (
          <></>
        )}
      </FlexDiv>
    </FlexDiv>
  );
};
