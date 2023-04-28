import styled, { css } from "styled-components";

const BorderLineDiv = styled.div`
  width: 100%;
  height: 3rem;
  display: flex;
  align-items: center;
  padding: 1rem;
  border-bottom: 0.0625rem solid var(--shad-color);
  background-color: var(--bg-color);
  cursor: pointer;
`;

const TagText = styled.span`
  text-indent: 0.75rem;
  text-align: center;
  cursor: pointer;
`;

interface TagType {
  tag: string;
  selectedTags: string[];
  setSelectedTags: (newData: string[]) => void;
}

export const TagSelectItem = ({
  tag,
  selectedTags,
  setSelectedTags,
}: TagType) => {
  return (
    <BorderLineDiv onClick={() => setSelectedTags([...selectedTags, tag])}>
      <TagText>{tag}</TagText>
    </BorderLineDiv>
  );
};
