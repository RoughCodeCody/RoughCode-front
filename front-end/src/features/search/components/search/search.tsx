import { FlexDiv } from "@/components/elements";

import { KeywordSearch } from "../keyword-search";
import { TagSearch } from "../tag-search";

type SearchProps = {
  whichTag: string;
};

export const Search = ({ whichTag }: SearchProps) => {
  return (
    <FlexDiv
      width="100%"
      maxWidth="1280px"
      direction="column"
      align="center"
      gap="1rem"
    >
      <KeywordSearch />
      <TagSearch whichTag={whichTag} />
    </FlexDiv>
  );
};
