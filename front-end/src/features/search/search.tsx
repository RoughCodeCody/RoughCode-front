import { KeywordSearch } from "./components";
import { TagSearch } from "./components";

import { FlexDiv } from "@/components/elements";

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
