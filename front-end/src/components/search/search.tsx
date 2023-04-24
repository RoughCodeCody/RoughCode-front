import { KeywordSearch } from "./keyword-search";
import { TagSearch } from "./tag-search";
import { FlexDiv } from "../elements";

export const Search = () => {
  return (
    <FlexDiv
      width="100%"
      maxWidth="1280px"
      direction="column"
      align="center"
      gap="1rem"
    >
      <KeywordSearch />
      <TagSearch />
    </FlexDiv>
  );
};
