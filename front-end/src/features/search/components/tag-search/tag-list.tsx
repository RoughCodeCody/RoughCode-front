import { TagSelectContainer } from "./style";
import { TagSelectItem } from "./tag-select-item";

import { useTags } from "../../api";

type TagListProps = {
  tagKeyword: string;
};
type tagResult = {
  tagId: number;
  name: string;
  cnt: number;
};

export const TagList = ({ tagKeyword }: TagListProps) => {
  const tagsQuery = useTags({ tagKeyword });
  if (tagsQuery.isLoading) {
    return <div></div>;
  }
  if (!tagsQuery?.data?.length)
    return (
      <TagSelectContainer>
        <TagSelectItem name={"Not Found"} />
      </TagSelectContainer>
    );
  return (
    <TagSelectContainer>
      {tagsQuery?.data?.map(({ tagId, name, cnt }: tagResult) => (
        <TagSelectItem key={tagId} tagId={tagId} name={name} cnt={cnt} />
      ))}
    </TagSelectContainer>
  );
};