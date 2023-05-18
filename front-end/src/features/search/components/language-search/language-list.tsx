import { LanguageSelectContainer } from "./style";
import { LanguageSelectItem } from "./language-select-item";

import { useLanguageTags } from "../../api";

type LanguageListProps = {
  whichTag: string;
  tagKeyword: string;
};
type tagResult = {
  languageId: number;
  name: string;
  cnt: number;
};

export const LanguageList = ({ whichTag, tagKeyword }: LanguageListProps) => {
  const languagesQuery = useLanguageTags({ whichTag, tagKeyword });
  if (languagesQuery.isLoading) {
    return <div></div>;
  }
  if (!languagesQuery?.data?.length)
    return (
      <LanguageSelectContainer>
        <LanguageSelectItem name={"검색된 태그가 없습니다"} />
      </LanguageSelectContainer>
    );
  return (
    <LanguageSelectContainer>
      {languagesQuery?.data?.map(({ languageId, name, cnt }: tagResult) => (
        <LanguageSelectItem
          key={languageId}
          languageId={languageId}
          name={name}
          cnt={cnt}
        />
      ))}
    </LanguageSelectContainer>
  );
};
