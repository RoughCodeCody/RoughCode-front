import { TagSelectContainer } from "./style";
import { TagSelectItem } from "./tag-select-item";

import { useTags } from "../../api/getTagList";

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
  console.log(tagsQuery);

  if (tagsQuery.isLoading) {
    return <div></div>;
  }
  if (!tagsQuery?.data?.result.length)
    return (
      <TagSelectContainer>
        <TagSelectItem name={"Not Found"} />
      </TagSelectContainer>
    );
  return (
    <TagSelectContainer>
      {tagsQuery?.data?.result.map(({ tagId, name, cnt }: tagResult) => (
        <TagSelectItem key={tagId} tagId={tagId} name={name} cnt={cnt} />
      ))}
    </TagSelectContainer>
  );
};
