import { useState } from "react";

import { DropdownArrow } from "@/components/elements";
import { FlexDiv } from "@/components/elements";

import { SearchInput } from "./style";

import { TagList } from "./tag-list";

export const TagSearchBox = () => {
  type SelectedTags = string[];
  // 더미데이터
  const dummyTags: SelectedTags = [
    "자바",
    "리액트",
    "파이썬",
    "씨쁠쁠",
    "스프링",
  ];
  const [isOpen, setisOpen] = useState<boolean>(false);
  const [tagKeyword, setTagKeyword] = useState("");

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTagKeyword(event.target.value);
  };

  return (
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
      <SearchInput
        onChange={handleChange}
        onFocus={() => setisOpen(true)}
        placeholder="태그 검색"
      />

      <DropdownArrow
        isOpen={isOpen}
        size={30}
        onClick={() => setisOpen(!isOpen)}
      />
      {isOpen ? <TagList tagKeyword={tagKeyword} /> : <></>}
    </FlexDiv>
  );
};
