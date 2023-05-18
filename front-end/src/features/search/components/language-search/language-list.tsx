import { LanguageSelectContainer } from "./style";
import { LanguageSelectItem } from "./language-select-item";

import { useTags } from "../../api";

type LanguageListProps = {
  whichTag: string;
  tagKeyword: string;
};
type tagResult = {
  tagId: number;
  name: string;
  cnt: number;
};

export const LanguageList = ({ whichTag, tagKeyword }: LanguageListProps) => {
  const languagesQuery = useTags({ whichTag, tagKeyword });
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
      {languagesQuery?.data?.map(({ tagId, name, cnt }: tagResult) => (
        <LanguageSelectItem key={tagId} tagId={tagId} name={name} cnt={cnt} />
      ))}
    </LanguageSelectContainer>
  );
};
