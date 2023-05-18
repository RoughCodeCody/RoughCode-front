import { FlexDiv } from "@/components/elements";
import { useRouter } from "next/router";

import { TagSearchBox } from "./tag-search-box";
import { SeletedTags } from "./selected-tags";
import { LanguageSearchBox } from "../language-search/language-search-box";
type TagSearchProps = {
  whichTag: string;
};

export const TagSearch = ({ whichTag }: TagSearchProps) => {
  const router = useRouter();
  const endPoint = router.asPath;

  return (
    <FlexDiv
      width="100%"
      maxWidth="70rem"
      justify="space-between"
      align="start"
      gap="1rem"
    >
      <SeletedTags />
      {endPoint === "/code-review" ? (
        <LanguageSearchBox whichTag={"language"} />
      ) : (
        <TagSearchBox whichTag={whichTag} />
      )}
    </FlexDiv>
  );
};
