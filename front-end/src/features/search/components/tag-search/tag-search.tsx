import { FlexDiv } from "@/components/elements";

import { TagSearchBox } from "./tag-search-box";
import { SeletedTags } from "./selected-tags";
import { LanguageSearchBox } from "../language-search/language-search-box";
type TagSearchProps = {
  whichTag: string;
};

export const TagSearch = ({ whichTag }: TagSearchProps) => {
  return (
    <FlexDiv
      width="100%"
      maxWidth="70rem"
      justify="space-between"
      align="start"
      gap="1rem"
    >
      <SeletedTags />
      <TagSearchBox whichTag={whichTag} />
    </FlexDiv>
  );
};
