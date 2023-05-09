import { useState } from "react";
import { BiSearchAlt } from "react-icons/bi";

import { FlexDiv } from "@/components/elements";
import { useSearchCriteriaStore } from "@/stores";

import { SearchBarForm, SearchInput, SearchButton } from "./style";

export const KeywordSearch = () => {
  const { setKeyword } = useSearchCriteriaStore();
  const [word, setWord] = useState("");

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setWord(event.target.value);
  };

  const enterListener = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      event.preventDefault();
      setKeyword(word);
    }
  };
  return (
    <FlexDiv width="100%" maxWidth="1280px">
      <FlexDiv
        width="100%"
        maxWidth="70rem"
        height="3.7rem"
        justify="space-between"
        gap="16px"
      >
        <SearchBarForm>
          <FlexDiv width="3.7rem" height="100%">
            <BiSearchAlt fontSize="2rem" color="var(--font-color)" />
          </FlexDiv>
          <SearchInput
            onChange={handleChange}
            onKeyPress={enterListener}
            placeholder="검색어를 입력해 주세요"
          />
        </SearchBarForm>
        <SearchButton onClick={() => setKeyword(word)}>검색</SearchButton>
      </FlexDiv>
    </FlexDiv>
  );
};
