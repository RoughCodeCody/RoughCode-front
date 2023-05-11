import { FlexDiv, TagChip } from "@/components/elements";
import { useSearchCriteriaStore } from "@/stores";

export const SeletedTags = () => {
  const { searchCriteria } = useSearchCriteriaStore();
  return (
    <FlexDiv gap="1rem" width="100%" height="100%" justify="start" wrap="wrap">
      {searchCriteria.tagIdList.map(({ tagId, name }) => (
        <TagChip key={tagId} tagId={tagId} name={name} />
      ))}
    </FlexDiv>
  );
};
