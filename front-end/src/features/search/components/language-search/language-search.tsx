import { FlexDiv, LanguageChip } from "@/components/elements";

import { LanguageSearchBox } from "./language-search-box";
// import { SeletedLanguage } from "./selected-language";
import { useSelectedLanguageStore } from "@/stores";

type LanguageSearchProps = {
  whichTag: string;
};

export const LanguageSearch = ({ whichTag }: LanguageSearchProps) => {
  const { selectedLanguage } = useSelectedLanguageStore();

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
        {selectedLanguage.map(({ tagId, name }) => {
          return <LanguageChip key={tagId} tagId={tagId} name={name} />;
        })}
      </FlexDiv>
      {/* <SeletedLanguage /> */}
      <LanguageSearchBox whichTag={whichTag} />
    </FlexDiv>
  );
};
