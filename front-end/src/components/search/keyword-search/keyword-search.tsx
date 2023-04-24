import { FlexDiv } from "@/components/elements";
import { SearchBarForm, SearchInput, SearchButton } from "./style";
import { Btn } from "@/components/elements/btn";

import { BiSearchAlt } from "react-icons/bi";

export const KeywordSearch = () => {
  return (
    <FlexDiv width="100%" maxWidth="1280px">
      <FlexDiv
        width="100%"
        maxWidth="1112px"
        height="58px"
        justify="space-between"
        gap="16px"
      >
        <SearchBarForm>
          <FlexDiv width="58px" height="100%">
            <BiSearchAlt fontSize="2rem" color="var(--font-color)" />
          </FlexDiv>
          <SearchInput placeholder="검색어를 입력해 주세요" />
        </SearchBarForm>
        <SearchButton>검색</SearchButton>
      </FlexDiv>
    </FlexDiv>
  );
};
