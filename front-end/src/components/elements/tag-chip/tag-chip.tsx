import styled, { css } from "styled-components";
import { TagChipContainer } from "./style";
import { Text } from "../text";
import { IoClose } from "react-icons/io5";

import { useSearchCriteriaStore } from "@/stores";

type TagChipProps = {
  tag: number;
};

export const TagChip = ({ tag }: TagChipProps) => {
  const { searchCriteria, deleteTagId } = useSearchCriteriaStore();

  return (
    <TagChipContainer>
      <Text size="1.2rem" color="bg" bold={true}>
        {tag}
      </Text>

      <IoClose
        onClick={() => {
          deleteTagId(tag);
        }}
        cursor="pointer"
        fontSize="1.3rem"
        color="var(--sub-two-color)"
        opacity="0.7"
      />
    </TagChipContainer>
  );
};
