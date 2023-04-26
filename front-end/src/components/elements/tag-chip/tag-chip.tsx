import styled, { css } from "styled-components";
import { TagChipContainer } from "./style";
import { Text } from "../text";
import { IoClose } from "react-icons/io5";

type TagChipProps = {
  tag: string;
  selectedTags: string[];
  setSelectedTags: React.Dispatch<React.SetStateAction<string[]>>;
};

export const TagChip = ({
  tag,
  selectedTags,
  setSelectedTags,
}: TagChipProps) => {
  return (
    <TagChipContainer>
      <Text size="1.2rem" color="bg" bold={true}>
        {tag}
      </Text>

      <IoClose
        onClick={() => {
          setSelectedTags(() => selectedTags.filter((item) => item !== tag));
          console.log("tag delete");
        }}
        cursor="pointer"
        fontSize="1.3rem"
        color="var(--sub-two-color)"
        opacity="0.7"
      />
    </TagChipContainer>
  );
};
