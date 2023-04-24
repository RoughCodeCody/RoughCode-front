import styled, { css } from "styled-components";
import { TagChipContainer } from "./style";
import { Text } from "../text";
import { IoClose } from "react-icons/io5";

type TagChipProps = {
  tag: string;
};

export const TagChip = ({ tag }: TagChipProps) => {
  return (
    <TagChipContainer>
      <Text size="1.2rem" color="bg" bold={true}>
        {tag}
      </Text>

      <IoClose
        onClick={() => {
          console.log("tag delete");
        }}
        cursor="pointer"
        fontSize="1.7rem"
        color="var(--bg-color)"
      />
    </TagChipContainer>
  );
};
