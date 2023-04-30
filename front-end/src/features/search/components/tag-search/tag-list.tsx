import { TagSelectContainer } from "./style";
import { TagSelectItem } from "./tag-select-item";

import { useTags } from "../../api/getTagList";

type TagListProps = {
  tagKeyword: string;
};

export const TagList = ({ tagKeyword }: TagListProps) => {
  const tagsQuery = useTags({ tagKeyword });

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
      {tagsQuery?.data?.result.map(({ tagId, name, cnt }) => (
        <TagSelectItem key={tagId} tagId={tagId} name={name} cnt={cnt} />
      ))}
    </TagSelectContainer>
  );
};
