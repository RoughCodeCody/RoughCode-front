import { IoClose } from "react-icons/io5";

import { TagChipContainer } from "./style";
import { Text } from "../text";
import { useSearchCriteriaStore } from "@/stores";

type TagChipProps = {
  tagId: number;
  name: string;
};

export const TagChip = ({ tagId, name }: TagChipProps) => {
  const { deleteTagId } = useSearchCriteriaStore();

  return (
    <TagChipContainer>
      <Text size="1.2rem" color="bg" bold={true}>
        {name}
      </Text>

      <IoClose
        onClick={() => {
          deleteTagId(tagId);
        }}
        cursor="pointer"
        fontSize="1.3rem"
        color="var(--sub-two-color)"
        opacity="0.7"
      />
    </TagChipContainer>
  );
};
