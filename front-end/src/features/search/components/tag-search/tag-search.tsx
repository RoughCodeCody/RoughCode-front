import { FlexDiv } from "@/components/elements";

import { TagSearchBox } from "./tag-search-box";
import { SeletectTags } from "./selected-tags";

export const TagSearch = () => {
  return (
    <FlexDiv
      width="100%"
      maxWidth="70rem"
      justify="space-between"
      align="start"
      gap="1rem"
    >
      <SeletectTags />
      <TagSearchBox />
    </FlexDiv>
  );
};